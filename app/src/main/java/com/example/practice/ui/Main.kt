package com.example.practice.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.practice.Product
import com.example.practice.ProductViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "homeScreen") {
        composable("homeScreen") {
            Main(navController)
        }
        composable("itemScreen/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")!!
            item_Screen( product = productId, navController = navController)
        }
        composable("like") {
            Like(navController)
        }
        composable("profile"){
            Profile(navController)
        }
        composable("categoryScreen"){
            CategoryScreen(navController)
        }
        composable("search/{productId}"){backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")!!
            search_Screen( product = productId, navController = navController)
        }
    }
}
@Composable
fun Main(navController: NavController
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("", "", "")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Person)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.Person)
    var isVisible by remember { mutableStateOf(true)}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
    ) {
        // Main content on top of the NavigationBar
        Column(
            modifier = Modifier.fillMaxWidth(),//.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    ){
                    // 카테고리 아이콘 추가
                    IconButton(onClick ={ navController.navigate("categoryScreen")})
                    {
                        Icon(
                            imageVector = Icons.Filled.List,
                            contentDescription = "카테고리 열기",
                            tint = Color.Black
                        )
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Ecompass",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }}

                Spacer(modifier = Modifier.height(10.dp))

                // Search Ba
                SearchBar(
                    onSearchBarClick = {
                        isVisible = it.isEmpty()
                    },
                    onSearchEnter = {
                        if (it.isNotEmpty()) {
                            navController.navigate("search/"+it)
                        }
                    }
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it }
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Choose Green With Confidence", style = MaterialTheme.typography.bodyLarge)
                    LazyVerticalGrid( // 각 항목의 최소 너비를 120.dp로 설정
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        columns = GridCells.Adaptive(minSize = 120.dp),
                        contentPadding = PaddingValues(30.dp, 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)// Item padding
                    ) {
                        items(itemList.size) { itemIndex ->
                            ItemCard(itemList[itemIndex],navController)
                        }
                    }}
            }
            AnimatedVisibility(
                visible = !isVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(text = "최근 검색어", style = MaterialTheme.typography.bodyLarge)

            }
        }

        // NavigationBar as a layer on top, but at the bottom of the screen
        NavigationBar(
            containerColor = Color(0x80E7E6ED),
            modifier = Modifier
                .align(Alignment.BottomCenter)  // Padding for spacing
                .clip(RoundedCornerShape(50.dp)) // Round the corners of the bar
                .height(60.dp)
                .width(200.dp)// Set height
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center ,
                verticalAlignment =  Alignment.CenterVertically // Center align the items in the row
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                                contentDescription = item,
                                modifier = Modifier.size(24.dp)// 아이콘 크기 조정
                            )
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            // 하트(좋아요) 아이콘 클릭 시 like 화면으로 이동
                            if (index == 1) {  // 1은 하트 아이콘의 인덱스
                                navController.navigate("like")
                            }
                            else if(index == 2) {
                                navController.navigate("profile")
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp)) // 둥근 모서리 적용
                            .padding(4.dp) // 아이템 간격 조정
                    )
                }
            }
        }
    }
}
@Composable
fun search_Screen(product : String,
                  navController: NavController,
                  viewModel: ProductViewModel = viewModel()
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("", "", "")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Person)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.Person)
    var isVisible by remember { mutableStateOf(true)}
    val products by viewModel.products.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.searchProductsByName(product) // 이 함수를 사용
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
    ) {
        // Main content on top of the NavigationBar
        Column(
            modifier = Modifier.fillMaxWidth(),//.verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
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
                    ){
                        // 카테고리 아이콘 추가
                        IconButton(onClick ={ navController.navigate("categoryScreen")})
                        {
                            Icon(
                                imageVector = Icons.Filled.List,
                                contentDescription = "카테고리 열기",
                                tint = Color.Black
                            )
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Ecompass",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(top = 10.dp)
                            )
                        }
                    }}

                Spacer(modifier = Modifier.height(10.dp))

                // Search Ba
                SearchBar(
                    onSearchBarClick = {
                        isVisible = it.isEmpty()
                    },
                    onSearchEnter = {
                        if (it.isNotEmpty()) {
                            navController.navigate("search/"+it)
                        }
                    }
                )
            }


            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInVertically(
                    initialOffsetY = { it }
                ),
                exit = slideOutVertically(
                    targetOffsetY = { it }
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = product+" 검색 결과", style = MaterialTheme.typography.bodyLarge)
                    LazyVerticalGrid( // 각 항목의 최소 너비를 120.dp로 설정
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        columns = GridCells.Adaptive(minSize = 120.dp),
                        contentPadding = PaddingValues(30.dp, 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(18.dp)// Item padding
                    ) {
                        items(products.size) { itemIndex ->
                            ItemCard2(products[itemIndex])
                        }
                    }}
            }
            AnimatedVisibility(
                visible = !isVisible,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Text(text = "최근 검색어", style = MaterialTheme.typography.bodyLarge)

            }
        }

        // NavigationBar as a layer on top, but at the bottom of the screen
        NavigationBar(
            containerColor = Color(0x80E7E6ED),
            modifier = Modifier
                .align(Alignment.BottomCenter)  // Padding for spacing
                .clip(RoundedCornerShape(50.dp)) // Round the corners of the bar
                .height(60.dp)
                .width(200.dp)// Set height
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center ,
                verticalAlignment =  Alignment.CenterVertically // Center align the items in the row
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                                contentDescription = item,
                                modifier = Modifier.size(24.dp)// 아이콘 크기 조정
                            )
                        },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            // 하트(좋아요) 아이콘 클릭 시 like 화면으로 이동
                            if (index == 1) {  // 1은 하트 아이콘의 인덱스
                                navController.navigate("like")
                            }
                            else if(index == 2) {
                                navController.navigate("profile")
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp)) // 둥근 모서리 적용
                            .padding(4.dp) // 아이템 간격 조정
                    )
                }
            }
        }
    }
}