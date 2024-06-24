package com.example.projectserotonin.dashboard

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projectserotonin.R
import com.example.projectserotonin.utils.SupplementType
import com.example.projectserotonin.components.AsyncImage
import com.example.projectserotonin.components.ProjectAppBar
import com.example.projectserotonin.components.SupplementStatusView
import com.example.projectserotonin.data.Supplement
import com.example.projectserotonin.data.UserAddedSupplement
import com.example.projectserotonin.ui.theme.SRTheme
import com.example.projectserotonin.ui.theme.SubTitleText2
import com.example.projectserotonin.utils.CommonUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : ComponentActivity() {

    private val viewmodel: DashboardViewModel by viewModels()

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.loadData()
        setContent {
            SRTheme {
                val pagerState: PagerState =
                    rememberPagerState(pageCount = { viewmodel.pagerCount })
                val response by viewmodel.dashboardResponse.collectAsStateWithLifecycle()

                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.background(SRTheme.colors.green)) {
                        ProjectAppBar(navigateUp = {
                            // Do Nothing
                        })
                        HorizontalPager(state = pagerState) { position ->
                            response?.let {
                                if (position == 0) {
                                    SupplementCard(items = it.supplements.all)
                                } else {
                                    UserAddedSupplementCard(items = it.userAddedSupplements)
                                }
                            }

                        }

                    }
                }
            }
        }
    }

    @Composable
    fun UserAddedSupplementCard(items: List<UserAddedSupplement>) {
        Column(
            modifier = Modifier
                .padding(horizontal =SRTheme.dimens.space8, vertical = SRTheme.dimens.space8)
                .clip(RoundedCornerShape(SRTheme.dimens.space16))
                .fillMaxWidth()
                .background(Color.White)
                .clickable {
                    viewmodel.navigateToRegimen(applicationContext)
                    Log.i("Pakka", "User Tapped on empty space")
                }
        ) {
            Spacer(modifier = Modifier.height(SRTheme.dimens.space24))
            SubTitleText2(
                text = "Additional Supplements",
                color = Color.Black,
                modifier = Modifier.padding(start = SRTheme.dimens.space10)
            )

            LazyVerticalGrid(
                contentPadding = PaddingValues(vertical = SRTheme.dimens.space16, horizontal = SRTheme.dimens.space5),
                columns = GridCells.Fixed(4)
            ) {
                itemsIndexed(items) { index, item ->
                    SupplementItemView(userAddedSupplement = item, type = SupplementType.UserAdded)
                    Spacer(modifier = Modifier.height(SRTheme.dimens.space24))
                }
            }
        }

    }

    @Composable
    fun SupplementCard(items: List<Supplement>) {
        Column(
            modifier = Modifier
                .padding(horizontal = SRTheme.dimens.space8, vertical = SRTheme.dimens.space8)
                .clip(RoundedCornerShape(SRTheme.dimens.space16))
                .fillMaxWidth()
                .background(Color.White)
                .clickable {
                    viewmodel.navigateToRegimen(applicationContext)
                    Log.i("Pakka", "User Tapped on empty space")
                }
        ) {
            Spacer(modifier = Modifier.height(SRTheme.dimens.space24))
            SubTitleText2(
                text = "Recommended Supplements",
                color = Color.Black,
                modifier = Modifier.padding(start = SRTheme.dimens.space10)
            )
            LazyVerticalGrid(
                contentPadding = PaddingValues(vertical = SRTheme.dimens.space16, horizontal = SRTheme.dimens.space5),
                columns = GridCells.Fixed(4)
            ) {
                itemsIndexed(items) { index, item ->
                    SupplementItemView(supplement = item, type = SupplementType.Recommended)
                    Spacer(modifier = Modifier.height(SRTheme.dimens.space24))
                }
            }
        }

    }

    @Composable
    fun SupplementItemView(
        supplement: Supplement? = null,
        userAddedSupplement: UserAddedSupplement? = null,
        type: SupplementType
    ) {
        if (type == SupplementType.UserAdded) {
            userAddedSupplement?.let {

                Box(
                    modifier = Modifier
                        .size(SRTheme.dimens.space100)
                        .padding(vertical = SRTheme.dimens.space10, horizontal = SRTheme.dimens.space5)
                        .clickable {
                            Toast
                                .makeText(applicationContext, it.product.name, Toast.LENGTH_SHORT)
                                .show()
                        }
                ) {
                    AsyncImage(url = it.product.imageLink, modifier = Modifier)
                    if (it.regimen.status.toUpperCase() == "ACTIVE" && it.consumed || it.regimen.status.toUpperCase() == "PAUSED") {

                        Column(
                            modifier = Modifier
                                .size(SRTheme.dimens.space28)
                                .align(Alignment.BottomCenter)
                                .offset(y = SRTheme.dimens.space10)
                        ) {
                            Image(
                                painter = painterResource(id = if (it.regimen.status.toUpperCase() == "ACTIVE" && it.consumed) R.drawable.icon_tick else R.drawable.icon_pause),
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .border(SRTheme.dimens.space3, Color.White, CircleShape)
                                    .shadow(SRTheme.dimens.space2)
                            )
                        }
                    }
                }
            }

        } else {
            supplement?.let {
                Box(
                    modifier = Modifier
                        .size(SRTheme.dimens.space100)
                        .padding(vertical = SRTheme.dimens.space10, horizontal = SRTheme.dimens.space5)
                        .clickable {
                            Toast
                                .makeText(applicationContext, it.name, Toast.LENGTH_SHORT)
                                .show()
                        }
                ) {
                    AsyncImage(url = it.image, modifier = Modifier)
                    val isConsumed = it.regimen.status.toUpperCase() == "ACTIVE" && it.consumed
                    val isPaused = it.regimen.status.toUpperCase() == "PAUSED"
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = SRTheme.dimens.space12)
                            .shadow(SRTheme.dimens.space2, CircleShape)
                    ) {
                        SupplementStatusView(isConsumed = isConsumed, isPaused = isPaused)
                    }

                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewmodel.checkAndRefreshData()
    }
}

