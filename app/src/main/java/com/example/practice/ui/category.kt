package com.example.practice.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController

@Composable
fun CategoryScreen(navController: NavController
) {
    var currentScreen by remember { mutableStateOf("CategoryScreen") }
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("", "", "")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Person)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.Person)
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color.White)
        )
        {
            TopBar()
            Spacer(modifier = Modifier.height(30.dp))
            DividerSection()
            Spacer(modifier = Modifier.height(16.dp))
            CategoryScreenList()
            DividerSection()
            Spacer(modifier = Modifier.height(16.dp))
            DividerSection()
        }
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
                            else if(index == 0) {
                                navController.navigate("homeScreen")
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
fun CategoryScreenList() {
    val categories = listOf(
        Category("주방용품", listOf("수세미", "설거지바", "지퍼백", "빨대")),
        Category("청소용품", listOf("탈취제")),
        Category("생활용품", listOf("칫솔", "샴푸바", "치약", "종이컵", "물티슈")),
        Category("육아용품"),
        Category("사무용품"),
        Category("위생용품")
    )

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(categories.size) { index ->
            CategoryItem(category = categories[index])
        }
    }

    DividerSection()
}

@Composable
fun CategoryItem(category: Category) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { isExpanded = !isExpanded },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text( // BasicText에서 Text로 변경
                text = category.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Black
            )
        }

        if (isExpanded) {
            category.subCategories?.forEach { subCategory ->
                Text(
                    text = subCategory, // BasicText에서 Text로 변경
                    modifier = Modifier
                        .padding(start = 32.dp, top = 8.dp, bottom = 8.dp),
                    fontSize = 18.sp,
                    color = Color.Black
                )

                DividerSection()
            }
        }
        DividerSection()
    }
}

data class Category(val name: String, val subCategories: List<String>? = null)
