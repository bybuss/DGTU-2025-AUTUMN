package bob.colbaskin.dgtu_2025_autumn.reports

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@Composable
fun ReportsScreen() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
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
}

@Composable
private fun ProjectStagesPieChart() {
    var data by remember {
        mutableStateOf(
            listOf(
                Pie(
                    label = "Минздрав",
                    data = 33.0,
                    color = Color(0xFF4A80D9),
                    selectedColor = Color(0xFF4A80D9)
                ),
                Pie(
                    label = "Планирование",
                    data = 32.0,
                    color = Color(0xFF499F88),
                    selectedColor = Color(0xFF499F88)
                ),
                Pie(
                    label = "Выполнение",
                    data = 32.0,
                    color = Color(0xFFB4DA5D),
                    selectedColor = Color(0xFFB4DA5D)
                ),
                Pie(
                    label = "Прочее",
                    data = 3.0,
                    color = Color(0xFF875BD2),
                    selectedColor = Color(0xFF875BD2)
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