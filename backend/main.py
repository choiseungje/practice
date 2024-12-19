import uvicorn
from fastapi import FastAPI, Depends, HTTPException, UploadFile
from sqlalchemy.orm import Session
from typing import List
import json
from sqlalchemy import desc
from starlette.middleware.cors import CORSMiddleware
import logging
import os
# db.py에서 필요한 항목 가져오기
from db import SessionLocal, Product, init_db, EcoInfo

# FastAPI 인스턴스 생성
app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # 모든 도메인에서 접근 허용
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)
# 데이터베이스 초기화
init_db()


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


# 크롤링한 데이터를 데이터베이스에 저장하는 엔드포인트
@app.post("/upload-json/")
async def upload_json(db: Session = Depends(get_db)):
    try:
        total_count = 0
        directory = r"C:\Users\com\Downloads\크롤링한 제품 리스트"
        # 디렉토리 내의 모든 JSON 파일 목록 가져오기
        json_files = [f for f in os.listdir(directory) if f.endswith('.json')]

        # 각 JSON 파일을 순회하며 처리
        for json_file in json_files:
            file_path = os.path.join(directory, json_file)

            # JSON 파일을 읽고 파싱
            with open(file_path, 'r', encoding='utf-8') as file:
                data = json.load(file)

                # JSON 데이터 처리
                for product_data in data:
                    # Product 객체 생성
                    product = Product(
                        name=product_data["name"],
                        price=product_data["price"],
                        image_url=product_data["image_url"],
                        link=product_data["link"],
                        rating=product_data["rating"],
                        eco_check=product_data["eco_check"]
                    )

                    # eco_info 리스트를 순회하며 EcoInfo 객체 생성 및 Product와 연결
                    for eco_info_data in product_data.get("eco_info", []):
                        eco_info = EcoInfo(
                            title=eco_info_data["title"],
                            link=eco_info_data["link"],
                            snippet=eco_info_data.get("snippet", "")
                        )
                        product.eco_info.append(eco_info)

                    # 데이터베이스에 Product 객체 추가
                    db.add(product)

                total_count += len(data)

        # 변경사항 커밋
        db.commit()

        return {"message": "Products saved successfully", "count": total_count}

    except json.JSONDecodeError:
        raise HTTPException(status_code=400, detail="Invalid JSON format in one of the files")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"An error occurred: {str(e)}")

@app.get("/", response_model=List[dict])
def read_products(db: Session = Depends(get_db)):
    # rating 기준으로 내림차순 정렬
    try:
        products = db.query(Product).order_by(desc(Product.rating)).all()
        return [
            {
                "id": product.id,
                "name": product.name,
                "price": product.price,
                "link": product.link,
                "image_url": product.image_url,
                "rating": product.rating,
                "eco_check": product.eco_check,
                "eco_info": [
                    {
                        "title": eco_info.title,
                        "link": eco_info.link,
                        "snippet": eco_info.snippet,
                    }
                    for eco_info in product.eco_info
                ],
            }
            for product in products
        ]
    except json.JSONDecodeError:
        logging.exception("Invalid JSON format")
        raise HTTPException(status_code=400, detail="Invalid JSON format")


@app.get("/{name}", response_model=List[dict])
def read_products_by_name(name: str, db: Session = Depends(get_db)):
    # Product 테이블에서 이름에 검색어가 포함된 항목 검색
    products = (db.query(Product).filter(Product.name.contains(name)).order_by(desc(Product.rating)).all())

    # JSON 형태로 변환
    return [
        {
            "id": product.id,
            "name": product.name,
            "price": product.price,
            "link": product.link,
            "image_url": product.image_url,
            "rating": product.rating,
            "eco_check": product.eco_check,
            "eco_info": [
                {
                    "title": eco_info.title,
                    "link": eco_info.link,
                    "snippet": eco_info.snippet,
                }
                for eco_info in product.eco_info
            ],
        }
        for product in products
    ]


@app.get("/{name}/{eco_check}", response_model=List[dict])
def read_products_by_name_and_bool(name: str, eco_check: bool, db: Session = Depends(get_db)):
    products = (db.query(Product).filter(Product.name.contains(name)).filter(Product.eco_check == eco_check).order_by(desc(Product.rating)).all()
    )

    # JSON 형태로 변환
    return [
        {
            "id": product.id,
            "name": product.name,
            "price": product.price,
            "link": product.link,
            "image_url": product.image_url,
            "rating": product.rating,
            "eco_check": product.eco_check,
            "eco_info": [
                {
                    "title": eco_info.title,
                    "link": eco_info.link,
                    "snippet": eco_info.snippet,
                }
                for eco_info in product.eco_info
            ],
        }
        for product in products
    ]
@app.get("/{name}/true}", response_model=List[dict])
def read_products_by_name_and_bool(name: str, db: Session = Depends(get_db)):
    # Product 테이블에서 이름에 검색어가 포함된 항목 검색
    products = (
        db.query(Product)
        .filter(Product.name.contains(name))
        .filter(Product.eco_check == "true")
        .order_by(desc(Product.rating))
        .all()
    )

    # JSON 형태로 변환
    return [
        {
            "id": product.id,
            "name": product.name,
            "price": product.price,
            "link": product.link,
            "image_url": product.image_url,
            "rating": product.rating,
            "eco_check": product.eco_check,
            "eco_info": [
                {
                    "title": eco_info.title,
                    "link": eco_info.link,
                    "snippet": eco_info.snippet,
                }
                for eco_info in product.eco_info
            ],
        }
        for product in products
    ]

if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)