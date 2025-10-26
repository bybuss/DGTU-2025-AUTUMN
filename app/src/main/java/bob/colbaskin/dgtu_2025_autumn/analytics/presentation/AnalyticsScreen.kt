package bob.colbaskin.dgtu_2025_autumn.analytics.presentation

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import bob.colbaskin.dgtu_2025_autumn.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import bob.colbaskin.dgtu_2025_autumn.common.design_system.theme.DGTU2025AUTUMNTheme
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.RowChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DotProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun AnalyticsScreenRoot(
    navController: NavController,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    AnalyticsScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                else -> Unit
            }
            viewModel.onAction(action)
        }
    ) 
}

@Composable
private fun AnalyticsScreen(
    state: AnalyticsState,
    onAction: (AnalyticsAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
            .padding(16.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.trk_logo_transparent),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Аналитика",
                    fontWeight = FontWeight.W700,
                    fontSize = 24.sp,
                    color = Color(0xFF101828)
                )
            }
        }

        item {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    KpiCard(
                        title = "Всего проектов",
                        value = state.totalProjects,
                        subtitle = "Активных: ${state.activeProjects}",
                        icon = R.drawable.folder
                    )
                }

                item {
                    KpiCard(
                        title = "Общая выручка",
                        value = state.totalAmount,
                        subtitle = "По всем проектам",
                        icon = R.drawable.dollar
                    )
                }

                item {
                    KpiCard(
                        title = "Общие затраты",
                        value = state.profit,
                        subtitle = "По всем проектам",
                        icon = R.drawable.arrow_up
                    )
                }

                item {
                    KpiCard(
                        title = "Прибыль",
                        value = state.dk,
                        subtitle = "Маржа: ${state.margin}",
                        icon = R.drawable.peoples
                    )
                }
            }
        }

        item {
            Text(
                "Распределение проектов по этапам",
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                color = Color(0xFF101828),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF0B0B0B)
                ),
                border = BorderStroke(1.dp, Color(0xFFD9DFE5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    ProjectStagesPieChart()
                }
            }
        }

        item {
            Text(
                "Динамика выручки и затрат",
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                color = Color(0xFF101828),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF0B0B0B)
                ),
                border = BorderStroke(1.dp, Color(0xFFD9DFE5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    RevenueCostsLineChart()
                }
            }
        }

        item {
            Text(
                "Выручка по проектам",
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                color = Color(0xFF101828),
                modifier = Modifier.padding(bottom = 16.dp).background(Color.White)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF0B0B0B)
                ),
                border = BorderStroke(1.dp, Color(0xFFD9DFE5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    ProjectsRevenueColumnChart()
                }
            }
        }

        item {
            Text(
                "Статистика по менеджерам",
                fontWeight = FontWeight.W600,
                fontSize = 18.sp,
                color = Color(0xFF101828),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF0B0B0B)
                ),
                border = BorderStroke(1.dp, Color(0xFFD9DFE5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    ManagersStatsRowChart()
                }
            }
        }
    }
}

@Composable
private fun KpiCard(
    title: String,
    value: String,
    subtitle: String,
    icon: Int
) {
    Card(
        modifier = Modifier
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color(0xFFD9DFE5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp,
                    color = Color(0xFF0B0B0B)
                )
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF5F6469)
                    )
                }
            }

            Column {
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp,
                    color = Color(0xFF101828)
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = Color(0xFF667085)
                )
            }
        }
    }
}

@Composable
private fun ProjectStagesPieChart() {
    var data by remember {
        mutableStateOf(
            listOf(
                Pie(
                    label = "Минздрав",
                    data = 33.0,
                    color = Color(0xFF4285F4),
                    selectedColor = Color(0xFF3367D6)
                ),
                Pie(
                    label = "Планирование",
                    data = 32.0,
                    color = Color(0xFF34A853),
                    selectedColor = Color(0xFF2E8B47)
                ),
                Pie(
                    label = "Выполнение",
                    data = 32.0,
                    color = Color(0xFFFBBC05),
                    selectedColor = Color(0xFFF9AB00)
                ),
                Pie(
                    label = "Прочее",
                    data = 3.0,
                    color = Color(0xFFEA4335),
                    selectedColor = Color(0xFFD93025)
                )
            )
        )
    }

    PieChart(
        modifier = Modifier
            .size(200.dp),
        data = data,
        onPieClick = {
            val pieIndex = data.indexOf(it)
            data = data.mapIndexed { mapIndex, pie ->
                pie.copy(selected = pieIndex == mapIndex)
            }
        },
        selectedScale = 1.1f,
        scaleAnimEnterSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        colorAnimEnterSpec = tween(200),
        colorAnimExitSpec = tween(200),
        scaleAnimExitSpec = tween(200),
        spaceDegreeAnimExitSpec = tween(200),
        style = Pie.Style.Fill
    )
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        data.forEach { pie ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(pie.color, shape = RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${pie.label}: ${pie.data}%",
                    fontSize = 12.sp,
                )
            }
        }
    }
}

@Composable
private fun RevenueCostsLineChart() {
    val data = remember {
        listOf(
            Line(
                label = "Выручка",
                values = listOf(3200.0, 4500.0, 3800.0, 5200.0, 4800.0, 6000.0),
                color = SolidColor(Color(0xFF4285F4)),
                firstGradientFillColor = Color(0xFF4285F4).copy(alpha = 0.3f),
                secondGradientFillColor = Color.Transparent,
                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                gradientAnimationDelay = 1000,
                drawStyle = DrawStyle.Stroke(width = 3.dp),
                dotProperties = DotProperties(
                    enabled = true,
                    color = SolidColor(Color.White),
                    strokeWidth = 2.dp,
                    radius = 5.dp,
                    strokeColor = SolidColor(Color(0xFF4285F4))
                )
            ),
            Line(
                label = "Затраты",
                values = listOf(2800.0, 3200.0, 3500.0, 3000.0, 4200.0, 2000.0),
                color = SolidColor(Color(0xFFEA4335)),
                firstGradientFillColor = Color(0xFFEA4335).copy(alpha = 0.3f),
                secondGradientFillColor = Color.Transparent,
                strokeAnimationSpec = tween(2000, easing = EaseInOutCubic),
                gradientAnimationDelay = 1500,
                drawStyle = DrawStyle.Stroke(width = 3.dp),
                dotProperties = DotProperties(
                    enabled = true,
                    color = SolidColor(Color.White),
                    strokeWidth = 2.dp,
                    radius = 5.dp,
                    strokeColor = SolidColor(Color(0xFFEA4335))
                )
            )
        )
    }

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        data = data,
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 400L
        }),
        curvedEdges = true
    )
}

@Composable
private fun ProjectsRevenueColumnChart() {
    val data = remember {
        listOf(
            Bars(
                label = "Выход",
                values = listOf(
                    Bars.Data(
                        label = "Выручка",
                        value = 600.0,
                        color = SolidColor(Color(0xFF4285F4))
                    )
                )
            ),
            Bars(
                label = "Номер",
                values = listOf(
                    Bars.Data(label = "Выручка", value = 10000.0, color = SolidColor(Color(0xFF34A853)))
                )
            ),
            Bars(
                label = "Объем",
                values = listOf(
                    Bars.Data(label = "Выручка", value = 8000.0, color = SolidColor(Color(0xFFFBBC05)))
                )
            ),
            Bars(
                label = "Итого",
                values = listOf(
                    Bars.Data(label = "Выручка", value = 500.0, color = SolidColor(Color(0xFFEA4335)))
                )
            )
        )
    }

    ColumnChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        data = data,
        barProperties = BarProperties(spacing = 12.dp),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
}

@Composable
private fun ManagersStatsRowChart() {
    val data = remember {
        listOf(
            Bars(
                label = "Мощность",
                values = listOf(
                    Bars.Data(label = "Показатель", value = 10.1, color = SolidColor(Color(0xFF4285F4)))
                )
            ),
            Bars(
                label = "Назначение",
                values = listOf(
                    Bars.Data(label = "Показатель", value = 10.1, color = SolidColor(Color(0xFF34A853)))
                )
            ),
            Bars(
                label = "Количество",
                values = listOf(
                    Bars.Data(label = "Показатель", value = 0.0, color = SolidColor(Color(0xFFFBBC05)))
                )
            )
        )
    }

    RowChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        data = data,
        barProperties = BarProperties(spacing = 16.dp),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
}

@Composable
@Preview(showBackground = true)
fun AnalyticsScreenPreview() {
    DGTU2025AUTUMNTheme {
        AnalyticsScreen(
            state = AnalyticsState(),
            onAction = {}
        )
    }
}
