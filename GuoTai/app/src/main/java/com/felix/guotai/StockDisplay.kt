package com.felix.guotai

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.felix.guotai.component.StockCard
import com.felix.guotai.remote.Remote
import com.felix.guotai.remote.model.Stock
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.sp

enum class StockSortOrder {
    CODE_ASC,
    CODE_DESC
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDisplay() {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var filterSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        var currentSortOrder by remember { mutableStateOf(StockSortOrder.CODE_ASC) }
        val stock = remember { mutableStateListOf<Stock>().apply { addAll(Remote.stock) } }

        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
            ),
            actions = {
                IconButton(onClick = {
                    filterSheet = true
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.menu),
                        contentDescription = "TopAppBar",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            title = {
                Text(
                    text = "國泰測驗 - 田乃武",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            items(stock) {
                StockCard(stock = it)
            }
        }

        // Bottom Sheet
        if (filterSheet) {
            ModalBottomSheet(
                onDismissRequest = { filterSheet = false },
                sheetState = sheetState,
                scrimColor = Color.Black.copy(alpha = 0.4f),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 32.dp)
                        .navigationBarsPadding()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SwapVert,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "股票排序",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(8.dp))
                    FilterOptionItem(
                        title = "依股票代號升序 (由小到大)",
                        isSelected = currentSortOrder == StockSortOrder.CODE_ASC,
                        onClick = {
                            currentSortOrder = StockSortOrder.CODE_ASC
                            stock.sortBy { it.code }
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) filterSheet = false
                            }
                        }
                    )
                    FilterOptionItem(
                        title = "依股票代號降序 (由大到小)",
                        isSelected = currentSortOrder == StockSortOrder.CODE_DESC,
                        onClick = {
                            currentSortOrder = StockSortOrder.CODE_DESC
                            stock.sortByDescending { it.code }
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) filterSheet = false
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun FilterOptionItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        )

        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}