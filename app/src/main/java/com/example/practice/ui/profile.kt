package com.example.practice.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.practice.R

@Composable
fun Profile(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(2) }
    val items = listOf("", "", "")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.Person)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.Person)
    val scrollState = rememberScrollState() // 스크롤 상태
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 10.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color.White)
                .verticalScroll(scrollState) // 스크롤 추가
        ) {
            TopBar()
            Spacer(modifier = Modifier.height(30.dp))
            DividerSection()
            ProfileSection()
            DividerSection()
            Spacer(modifier = Modifier.height(16.dp))
            CommunitySection()
            Spacer(modifier = Modifier.height(16.dp))
            ShopSection()
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
                            if (index == 1) {
                                navController.navigate("like")
                            }
                            else if (index == 0) {
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
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ecompass",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun ProfileSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = "나여주", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "cosmicluna", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

@Composable
fun DividerSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Black)
    )
}

@Composable
fun CommunitySection() {
    Column(
        modifier = Modifier
            .wrapContentHeight() // 높이 무한 제약 방지
            .padding(8.dp)
    ) {
        Text(
            text = "커뮤니티",
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        DividerSection()
        SectionItem(icon = Icons.Default.Create, title = "내가 쓴 글")
        SectionItem(icon = Icons.Default.MailOutline, title = "댓글 단 글")
        SectionItem(icon = Icons.Default.Star, title = "스크랩")
    }
}




@Composable
fun ShopSection() {
    Column{
        Text(text = "상점",
            modifier = Modifier
                .padding(8.dp),
            color = Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        DividerSection()
        SectionItem(icon = Icons.Default.ShoppingCart, title = "주문 목록") // 주문 목록
        SectionItem(icon = Icons.Default.FavoriteBorder, title = "선물함")     // 선물함
        SectionItem(icon = Icons.Default.Email, title = "리뷰 관리")     // 리뷰 관리
        SectionItem(icon = Icons.Default.AccountBox, title = "결제수단")       // 결제수단
        SectionItem(icon = Icons.Default.CheckCircle, title = "할인쿠폰")    // 할인쿠폰
        SectionItem(icon = Icons.Default.Info, title = "공지사항")  // 공지사항
        SectionItem(icon = Icons.Default.Settings, title = "고객센터")  // 고객센터
    }

}

@Composable
fun SectionItem(icon: ImageVector, title: String) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { /* Handle Click */ },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                modifier = Modifier.size(30.dp), // 아이콘 카드 크기
                colors = CardDefaults.cardColors(Color.LightGray), // 아이콘 배경색
                shape = RoundedCornerShape(6.dp), // 동그란 아이콘 카드
            ) {
                Box( // Box로 가운데 정렬
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // 콘텐츠를 중앙에 배치
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier
                            .size(24.dp)
                            .align(alignment = Alignment.Center), // 아이콘 가운데 정렬
                        tint = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, fontSize = 16.sp)
        }
        DividerSection()
    }
}
