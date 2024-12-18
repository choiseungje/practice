package com.example.practice.ui

import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.Product
import com.squareup.picasso.Picasso
import android.widget.ImageView
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practice.ProductViewModel

@Composable
fun ItemCard(product: ProductItem, navController: NavController) {
    Card(
        modifier = Modifier
            .size(width = 140.dp, height = 170.dp)
            .padding(10.dp)
            .clickable { navController.navigate("itemScreen/"+product.name) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2EDE4))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            semi_ImageCard(product.imageUrl)

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,  // 글씨 굵게
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}

fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
    Picasso.get()
        .load(imageUrl)
        .into(imageView)
}

data class ProductItem(
    val name: String,
    val imageUrl: String
)

val itemList = listOf(
    ProductItem(
        name = "디퓨저",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2308716629/display_1500/stock-vector-set-of-diffuser-bottles-with-air-freshener-vector-2308716629.jpg"
    ),
    ProductItem(
        name = "화장지",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2543615663/display_1500/stock-vector-toilet-paper-icon-flat-line-illustration-2543615663.jpg"
    ),
    ProductItem(
        name = "숟가락",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2510571417/display_1500/stock-vector-spoon-silhouette-spoon-vector-spoon-icon-2510571417.jpg"
    ),
    ProductItem(
        name = "목걸이",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2527560059/display_1500/stock-vector-necklace-icon-isolated-on-a-white-background-vector-illustration-2527560059.jpg"
    ),
    ProductItem(
        name = "스크래퍼",
        imageUrl = "data:image/jpeg;base64,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"
    ),
    ProductItem(
        name = "빗자루",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2514240521/display_1500/stock-vector-old-witch-broom-outline-doodle-line-art-halloween-broomstick-graphics-illustration-easy-coloring-2514240521.jpg"
    ),
    ProductItem(
        name = "걸레",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2445157153/display_1500/stock-vector-mop-icon-simple-illustration-of-mop-vector-icon-for-web-design-isolated-on-white-background-2445157153.jpg"
    ),
    ProductItem(
        name = "종이컵",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2347667337/display_1500/stock-vector-realistic-blank-paper-cup-mockup-with-double-side-wall-coffee-to-go-take-out-mug-vector-2347667337.jpg"
    ),
    ProductItem(
        name = "옷걸이",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2508020901/display_1500/stock-vector-hanger-line-black-icon-isolated-on-white-background-wardrobe-sign-clothes-rack-symbol-or-2508020901.jpg"
    ),
    ProductItem(
        name = "의자",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2494177959/display_1500/stock-vector--chair-silhouette-chair-flat-icon-chair-symbol-2494177959.jpg"
    ),
    ProductItem(
        name = "램프",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2531526871/display_1500/stock-vector-light-buld-idea-icon-on-isolated-background-idea-symbol-solution-electricity-light-electric-2531526871.jpg"
    ),
    ProductItem(
        name = "유아 용품",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2534567823/display_1500/stock-vector-cartoon-character-of-cute-baby-smiling-happily-while-shampooing-or-hair-washing-with-foam-on-the-2534567823.jpg"
    ),
    ProductItem(
        name = "업사이클링 백팩",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2505672531/display_1500/stock-vector-vector-illustration-of-yellow-backpack-isolated-on-white-background-back-to-school-flat-2505672531.jpg"
    ),
    ProductItem(
        name = "필통",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2379872277/display_1500/stock-vector-set-of-stationery-pen-pencil-eraser-ruler-correction-fluid-pencil-box-illustrations-line-2379872277.jpg"
    ),
    ProductItem(
        name = "볼펜",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2281714621/display_1500/stock-vector-red-pen-vector-icon-classic-red-pen-design-2281714621.jpg"
    ),
    ProductItem(
        name = "칫솔",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2523419721/display_1500/stock-vector-toothbrush-icon-illustration-flat-design-style-2523419721.jpg"
    ),
    ProductItem(
        name = "간식",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2503552841/display_1500/stock-vector-chips-snack-cartoon-vector-icon-illustration-food-object-icon-concept-isolated-premium-vector-2503552841.jpg"
    ),
    ProductItem(
        name = "물병",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2410532281/display_1500/stock-vector-doodle-flat-clipart-simple-illustration-of-an-empty-glass-jug-2410532281.jpg"
    ),
    ProductItem(
        name = "포장지",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2521255413/display_1500/stock-vector-textile-roll-vector-icon-paper-tube-illustration-design-for-kitchen-cleaning-towel-carpet-scroll-2521255413.jpg"
    ),
    ProductItem(
        name = "나무젓가락",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2505982533/display_1500/stock-vector-cartoon-pair-of-wooden-chopsticks-elegantly-tapered-with-tips-reflecting-balance-between-2505982533.jpg"
    ),
    ProductItem(
        name = "그릇",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2532111999/display_1500/stock-vector-bowl-icon-set-collection-in-black-design-containing-rice-in-bowl-hot-soup-icon-empty-bowl-icon-2532111999.jpg"
    ),
    ProductItem(
        name = "마스카라",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2471959281/display_1500/stock-vector-makeup-mascara-vector-icon-open-tube-of-mascara-sign-vector-flat-logo-of-makeup-black-simple-2471959281.jpg"
    ),
    ProductItem(
        name = "파우더",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2527251797/display_1500/stock-vector-face-powder-icon-sign-vector-2527251797.jpg"
    ),
    ProductItem(
        name = "오일",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2391155953/display_1500/stock-vector-spray-antifreeze-oil-bottle-beauty-icon-2391155953.jpg"
    ),
    ProductItem(
        name = "샴푸",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2522289287/display_1500/stock-vector-liquid-soap-or-shampoo-icon-for-pets-plastic-bottle-with-paw-print-and-drop-2522289287.jpg"
    ),
    ProductItem(
        name = "샴푸바",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2481092147/display_1500/stock-vector-soap-foam-isolated-on-transparent-background-set-of-bath-foam-with-shampoo-bubbles-soap-gel-or-2481092147.jpg"
    ),
    ProductItem(
        name = "비누",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2502678497/display_1500/stock-vector-soap-bar-vector-icon-bathroom-soap-bar-vector-illustration-hygiene-soap-bar-icon-2502678497.jpg"
    ),
    ProductItem(
        name = "고무장갑",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2392604011/display_1500/stock-vector-red-gloves-vektor-illustrasi-cartoon-2392604011.jpg"
    ),
    ProductItem(
        name = "세제",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2503558555/display_1500/stock-vector-a-bottle-of-detergent-is-depicted-in-a-cartoon-style-liquid-laundry-detergent-no-label-2503558555.jpg"
    ),
    ProductItem(
        name = "스프레이",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2527505491/display_1500/stock-vector-spray-can-line-icon-aerosol-paint-graffiti-spray-paint-tubes-chemicals-art-urban-street-2527505491.jpg"
    ),
    ProductItem(
        name = "세정제",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2451119617/display_1500/stock-vector-hands-washing-soap-bubbles-and-hands-vector-illustration-2451119617.jpg"
    ),
    ProductItem(
        name = "립스틱",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2551254269/display_1500/stock-vector-isolated-cute-colored-lipstick-in-tube-lipstick-in-flat-style-2551254269.jpg"
    ),
    ProductItem(
        name = "수세미",
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2492080241/display_1500/stock-vector-scrubbing-brush-icon-linear-logo-mark-in-black-and-white-2492080241.jpg"
    ),
)


@Composable
fun ItemCard2(product: Product) {
    val uriHandler = LocalUriHandler.current
    Card(
        modifier = Modifier
            .size(width = 140.dp, height = 170.dp)
            .padding(10.dp)
            .clickable { uriHandler.openUri(product.link) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2EDE4))
    ) {Column(
        modifier = Modifier
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row {
        ImageCard(product.image_url)
        Column (
            modifier = Modifier
               .padding(3.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.Start
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "rating",
                    modifier = Modifier.size(14.dp),
                    tint = Color(0xFF000000)
                )
                Text(
                    text = product.rating,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 2.dp)
                )
            }
            Text(
                text = "${product.price}원",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        }}
            Text(
                text = product.name,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
            )



            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}

@Composable
fun item_Screen(product : String,
                navController: NavController,
                viewModel: ProductViewModel = viewModel()) {

    val uriHandler = LocalUriHandler.current
    var currentScreen by remember { mutableStateOf("bamboo") }
    var currentTab by remember { mutableStateOf(0) } // 0: 상품정보, 1: 구매사이트
    var selectedItem by remember { mutableIntStateOf(0) }
    val matchingItems = itemList.filter { it.name == product }

    Box(modifier = Modifier.fillMaxSize()) {
        // 첫 번째 화면: 대나무 화면
        AnimatedVisibility(
            visible = currentScreen == "bamboo",
            enter = fadeIn(tween(1000)) + slideInHorizontally(initialOffsetX = { 1000 }), // 오른쪽에서 왼쪽으로 슬라이드
            exit = fadeOut(tween(1000)) + slideOutHorizontally(targetOffsetX = { -1000 }) // 왼쪽으로 슬라이드
        ) {
            val products by viewModel.products.collectAsState(initial = emptyList())

            LaunchedEffect(Unit) {
                viewModel.eco_false(matchingItems[0].name, true) // 이 함수를 사용
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start // 아이콘을 왼쪽 정렬
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp) // 필요에 따라 높이를 조정
                    ) {
                        // 카테고리 아이콘 추가

                        IconButton(
                            onClick = { navController.navigate("homeScreen") }
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Ecompass",
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 상단 텍스트
                CustomNavigationBar(
                    items = listOf("친환경", "일반"),
                    selectedIcons = listOf("친환경", "일반"),
                    unselectedIcons = listOf("친환경", "일반"),
                    selectedItem = selectedItem,
                    onItemSelected = { index ->
                        selectedItem = index
                        if (index == 1) {
                            currentScreen = "newScreen"
                        } else {
                            currentScreen = "bamboo"
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Big_ImageCard(matchingItems[0].imageUrl)
                Spacer(modifier = Modifier.height(16.dp))

                // 제목 텍스트
                androidx.compose.material.Text(
                    text = product,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,  // 글씨 굵게
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))


                // 탭 네비게이션 바
                CustomNavigationBar(
                    items = listOf("상품정보", "구매사이트"),
                    selectedIcons = listOf("상품정보", "구매사이트"),
                    unselectedIcons = listOf("상품정보", "구매사이트"),
                    selectedItem = currentTab,
                    onItemSelected = { index ->
                        currentTab = index
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                when (currentTab) {
                    0 -> {
                        // 상품정보 화면
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                                .height(400.dp)
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState()) // 스크롤 추가
                        ) {
                            StyledText(
                                title = "주요 특징",
                                content = buildAnnotatedString {
                                    if (products.isNotEmpty() && products[0].eco_info.isNotEmpty()) {
                                        for (number in products[0].eco_info) {
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append("• " + number.title)
                                            }
                                            append(": " + number.snippet + "\n\n")
                                        }
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.height(16.dp))


                        }
                    }

                    1 -> {
                        // 구매사이트 화면
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 140.dp),
                            modifier = Modifier.fillMaxSize(), // 크기 조정
                            contentPadding = PaddingValues(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(products.size) { index ->
                                ItemCard2(product = products[index])
                                // 구매 사이트 네비게이션 로직 추가
                            }
                        }
                    }
                }
            }
        }

        // 두 번째 화면: 새 화면
        AnimatedVisibility(
            visible = currentScreen == "newScreen",
            enter = fadeIn(tween(1000)) + slideInHorizontally(initialOffsetX = { -1000 }), // 왼쪽에서 오른쪽으로 슬라이드
            exit = fadeOut(tween(1000)) + slideOutHorizontally(targetOffsetX = { 1000 }) // 오른쪽으로 슬라이드
        ) {
            val products by viewModel.products.collectAsState(initial = emptyList())

            LaunchedEffect(Unit) {
                viewModel.eco_false(product, false) // 이 함수를 사용
            }

            if (products.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 뒤로 가기 버튼
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start // 아이콘을 왼쪽 정렬
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp) // 필요에 따라 높이를 조정
                        ) {
                            IconButton(
                                onClick = { navController.navigate("homeScreen") }
                            ) {
                                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                            }

                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Ecompass",
                                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(top = 10.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 상단 텍스트
                    CustomNavigationBar(
                        items = listOf("친환경", "일반"),
                        selectedIcons = listOf("친환경", "일반"),
                        unselectedIcons = listOf("친환경", "일반"),
                        selectedItem = selectedItem,
                        onItemSelected = { index ->
                            selectedItem = index
                            if (index == 0) {
                                currentScreen = "bamboo"
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 이미지
                    Big_ImageCard(products[0].image_url)

                    Spacer(modifier = Modifier.height(16.dp))

                    // 제목 텍스트
                    Text(
                        text = products[0].name,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // 설명 텍스트를 스크롤 가능하게 변경
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(8.dp))
                            .height(300.dp)
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()) // 스크롤 추가
                    ) {
                        Text(
                            text = "물과 습기에 강해 오래 사용할 수 있고 손잡이가 쉽게 변형되지 않아 실용적임.\n\n" +
                                    "상대적으로 저렴한 가격에 제공됨.\n\n" +
                                    "하지만, 사용 후 폐기물이 환경에 큰 영향을 미침.\n\n" +
                                    "일부 저렴한 플라스틱 칫솔은 제조 과정에서 유해 화학 물질이 포함될 수 있어 건강에 해로울 가능성이 있음.",
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 버튼
                    Button(
                        onClick = { uriHandler.openUri(products[0].link)},
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFA1B5D8)
                        )
                    ) {
                        Text("구매하러 가기", color = Color.White)
                    }
                }
            } else {
                // 빈 리스트일 경우 표시할 UI
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "상품이 없습니다.",
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                        color = Color.Gray
                    )
                }
            }
        }
    }}
@Composable
fun StyledText(title: String, content: AnnotatedString) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        androidx.compose.material.Text(
            text = title,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF000000)
        )
        Spacer(modifier = Modifier.height(4.dp))
        androidx.compose.material.Text(
            text = content,
            style = MaterialTheme.typography.body2.copy(lineHeight = 20.sp),
            color = Color.Black
        )
    }
}
@Composable
fun CustomNavigationBar(
    items: List<String>,
    selectedIcons: List<String>,
    unselectedIcons: List<String>,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selectedItem: Int = 0
) {
    val backgroundColor = Color(0x80E7E6ED)
    val selectedItemColor = Color(0xFFA1B5D8)

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .height(60.dp)
            .width(200.dp),
        color = backgroundColor,
        shape = RoundedCornerShape(50.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, _ ->
                NavigationItem(
                    isSelected = selectedItem == index,
                    selectedText = selectedIcons[index],
                    unselectedText = unselectedIcons[index],
                    selectedBackgroundColor = selectedItemColor,
                    onItemClick = { onItemSelected(index) }
                )
            }
        }
    }
}
@Composable
private fun NavigationItem(
    isSelected: Boolean,
    selectedText: String,
    unselectedText: String,
    selectedBackgroundColor: Color,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (isSelected) selectedBackgroundColor else Color.Transparent)
            .clickable(onClick = onItemClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = if (isSelected) selectedText else unselectedText,
            color = if (isSelected) Color.White else Color.Black,
            textAlign = TextAlign.Center,
            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchBarClick: (String) -> Unit={},
    onSearchEnter: (String) -> Unit={} // 엔터 이벤트 처리
) {
    var searchText by remember { mutableStateOf("") }

    androidx.compose.material.OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchBarClick(it)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .onKeyEvent { event ->
                if (event.key == Key.Enter) {
                    onSearchEnter(searchText)  // 엔터 키를 눌렀을 때 처리
                    true
                } else {
                    false
                }
            },
        placeholder = { Text("검색어를 입력하세요") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { searchText = "" }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Text",
                        tint = Color.Gray
                    )
                }
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF6200EE),
            unfocusedBorderColor = Color.Gray,
            backgroundColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp),
        singleLine = true,
    )
}

@Composable
fun LoadImageComposable(imageUrl: String) {
    val context = LocalContext.current
    AndroidView(
        factory = { ctx ->
            ImageView(ctx).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        },
        update = { imageView ->
            imageView.layoutParams = ViewGroup.LayoutParams(10, 10)
            Picasso.get()
                .load(imageUrl)
                .placeholder(android.R.color.darker_gray) // 로딩 중 색상
                .error(android.R.color.holo_red_dark) // 오류 시 색상
                .into(imageView)
        },
        modifier = Modifier.size(20 .dp) // 이미지 크기
    )
}
@Composable
fun ImageCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp,top=10.dp), // 카드 외부에 여백 추가
        shape = RoundedCornerShape(8.dp)
    ) {
        Surface(
            modifier = Modifier
                .width(60.dp) // Surface의 가로 크기 설정
                .height(60.dp) // Surface의 세로 크기 설정
        ) {
            LoadImageComposable(
                imageUrl = imageUrl // 이미지 URL
            )
        }
    }
}


@Composable
fun Big_ImageCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp,top=10.dp), // 카드 외부에 여백 추가
        shape = RoundedCornerShape(8.dp)
    ) {
        Surface(
            modifier = Modifier
                .width(170.dp) // Surface의 가로 크기 설정
                .height(170.dp) // Surface의 세로 크기 설정
        ) {
            LoadImageComposable(
                imageUrl = imageUrl // 이미지 URL
            )
        }
    }
}
@Composable
fun semi_ImageCard(imageUrl: String) {
    Card(
        modifier = Modifier
            .padding(start = 10.dp,top=10.dp), // 카드 외부에 여백 추가
        shape = RoundedCornerShape(8.dp)
    ) {
        Surface(
            modifier = Modifier
                .width(90.dp) // Surface의 가로 크기 설정
                .height(90.dp) // Surface의 세로 크기 설정
        ) {
            LoadImageComposable(
                imageUrl = imageUrl // 이미지 URL
            )
        }
    }
}