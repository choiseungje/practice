from sqlalchemy import Column, Integer, String, Boolean, ForeignKey, Text, create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, relationship

# SQLite 데이터베이스 설정
DATABASE_URL = "sqlite:///./test3.db"
engine = create_engine(DATABASE_URL, connect_args={"check_same_thread": False})
SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()


class Product(Base):
    __tablename__ = "products"

    id = Column(Integer, primary_key=True, index=True)  # 제품 고유 ID
    name = Column(String, nullable=False)  # 제품 이름
    price = Column(String, nullable=False)  # 제품 가격
    link = Column(Text, nullable=False)  # 제품 링크
    image_url = Column(Text, nullable=False)  # 제품 이미지 URL
    rating = Column(String, nullable=False)  # 제품 평점
    eco_check = Column(Boolean, nullable=False)  # 친환경 여부

    eco_info = relationship("EcoInfo", back_populates="product", cascade="all, delete-orphan")

# 친환경 정보 모델 정의
class EcoInfo(Base):
    __tablename__ = "eco_infos"

    id = Column(Integer, primary_key=True, index=True)  # 친환경 정보 고유 ID
    title = Column(String, nullable=False)  # 친환경 정보 제목
    link = Column(Text, nullable=False)  # 친환경 정보 링크
    snippet = Column(Text, nullable=True)  # 친환경 정보 요약
    product_id = Column(Integer, ForeignKey("products.id", ondelete="CASCADE"))  # 연결된 제품 ID

    # Product와 관계 설정
    product = relationship("Product", back_populates="eco_info")

# 데이터베이스 테이블 생성
def init_db():
    Base.metadata.create_all(bind=engine)
