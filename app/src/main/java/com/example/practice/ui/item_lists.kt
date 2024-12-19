package com.example.practice.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.practice.ProductViewModel

@Composable
fun item_Screen(product : String,
                navController: NavController,
                viewModel: ProductViewModel = viewModel()
) {

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
                        StyledText(
                            title = "주요 특징",
                            content = buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("• " + "높은 탄소 발자국")
                                }
                                append(": " + "일반 제품은 생산 과정에서 더 많은 온실가스를 배출하여 기후 변화에 부정적인 영향을 미칩니다. 반면, 친환경 제품은 생산부터 폐기까지의 전 과정에서 환경 영향을 최소화하도록 설계되어 있습니다." + "\n\n")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("• " + "자원 소모 및 폐기물 증가")
                                }
                                append(": " + "일회용 플라스틱 제품과 같은 일반 제품은 사용 후 폐기되어 환경 오염을 유발합니다. 반면, 텀블러나 에코백과 같은 친환경 제품은 재사용이 가능하여 폐기물 생성을 줄입니다. " + "\n\n")
                                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                    append("• " + "유해 화학물질 사용")
                                }
                                append(": " + "일반 제품은 생산 과정에서 유해 화학물질을 사용할 수 있어 환경과 인체에 해로울 수 있습니다. 반면, 친환경 제품은 이러한 유해 물질의 사용을 최소화하여 안전성을 높입니다. " + "\n\n")
                            }
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
    }
}