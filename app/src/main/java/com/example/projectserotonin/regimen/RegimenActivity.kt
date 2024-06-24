package com.example.projectserotonin.regimen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.projectserotonin.R
import com.example.projectserotonin.components.AsyncImage
import com.example.projectserotonin.components.ProjectAppBar
import com.example.projectserotonin.components.SRAlertDialog
import com.example.projectserotonin.components.SupplementActionView
import com.example.projectserotonin.components.SupplementToast
import com.example.projectserotonin.components.debounceClick
import com.example.projectserotonin.data.Item
import com.example.projectserotonin.data.ItemsToConsume
import com.example.projectserotonin.ui.theme.BodyText
import com.example.projectserotonin.ui.theme.HeaderText
import com.example.projectserotonin.ui.theme.LabelText
import com.example.projectserotonin.ui.theme.SRTheme
import com.example.projectserotonin.ui.theme.SubTitleText2
import com.example.projectserotonin.utils.CommonUtils
import com.example.projectserotonin.utils.TagUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class RegimenActivity : ComponentActivity() {
    private val viewmodel: RegimenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.checkForRefreshData()
        setContent {
            SRTheme {
                initViewsWithViewMode(viewModel = viewmodel)
            }
        }
    }

    @Composable
    fun initViewsWithViewMode(viewModel: RegimenViewModel) {
        val showAppBar by viewmodel.showAppBar.collectAsStateWithLifecycle()

        Surface(modifier = Modifier.fillMaxSize()) {
            Column() {
                if (showAppBar) {
                    ProjectAppBar(canNavigateBack = true, navigateUp = {
                        Log.i("Pakka", "Back Button Taped")
                        this@RegimenActivity.onBackPressedDispatcher.onBackPressed()
                    })
                }
                RegimenView()
            }
        }
    }


    @Composable
    fun RegimenView() {
        val response by viewmodel.regimenResponse.collectAsStateWithLifecycle()
        val showDeleteDialog by viewmodel.showAlert.collectAsStateWithLifecycle()
        val showCustomToast by viewmodel.showCustomToast.collectAsStateWithLifecycle()
        val showConsumptionAnimatedView by viewmodel.showConsumedAnimationView.collectAsStateWithLifecycle()

        Box {
            response?.list?.let { data ->
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(data) { itemToConsume ->
                        IndividualCard(itemsToConsume = itemToConsume)
                    }
                }
            }

            if (showDeleteDialog) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    SRAlertDialog(

                        confirmButton = {
                            Log.i("Pakka", "Delete Confirm Button Taped")
                            val id = viewmodel.currentSupplementItem
                            val section = viewmodel.currentSection
                            if (id != null && section.isNullOrEmpty().not()) {
                                viewmodel.consumedSupplement(id, section!!, false)
                            }
                        },
                        dismissButton = {
                            Log.i("Pakka", "Delete Cancel Button Taped")
                            viewmodel.deleteConsumptionTapped(false)
                        },
                        positiveText = stringResource(id = R.string.delete),
                        negativeText = stringResource(
                            id = R.string.cancel
                        )
                    )
                }
            }

            if (showCustomToast) {
                Column(
                    modifier = Modifier
                        .padding(SRTheme.dimens.space10)
                        .fillMaxSize()
                        .debounceClick {
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    SupplementToast(viewmodel.currentSupplementItem, onTap = {
                        viewmodel.updateConsumptionAnimatedView(true)
                        viewmodel.updateShowAppBar(true)

                    })
                }
            }

            if (showConsumptionAnimatedView) {
                viewmodel.updateShowAppBar(false)
                viewmodel.currentSupplementItem?.let {
                    ConsumptionAnimatedView(item = it)
                }
            }

        }
    }

    @Composable
    fun IndividualCard(itemsToConsume: ItemsToConsume) {
        var list = itemsToConsume.items
        var title = itemsToConsume.title
        var code = itemsToConsume.code
        var cardBackgroundColor = SRTheme.colors.dullWhite
        when (code) {
            "DAYTIME" -> cardBackgroundColor = SRTheme.colors.blueSky
            "NIGHT_TIME" -> cardBackgroundColor = SRTheme.colors.darkblue
            "ON_DEMAND" -> cardBackgroundColor = SRTheme.colors.pink
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardBackgroundColor)
        ) {

            Column(modifier = Modifier.padding(horizontal = SRTheme.dimens.space8)) {
                Spacer(modifier = Modifier.height(SRTheme.dimens.space12))
                SubTitleText2(
                    text = title,
                    color = if (code == "NIGHT_TIME") SRTheme.colors.onPrimary else SRTheme.colors.contentPrimary
                )
                Spacer(modifier = Modifier.height(SRTheme.dimens.space2))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(SRTheme.dimens.space14)) {
                    items(list) {
                        SupplementItemView(item = it, type = code)
                    }

                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }

    @Composable
    fun ConsumptionAnimatedView(item: Item) {
        val configuration = LocalConfiguration.current
        var currentProgress by remember { mutableFloatStateOf(0f) }
        var isConsumed by remember { mutableStateOf(false) }
        var showSize by remember {
            mutableStateOf(false)
        }

        var offsetY by remember { mutableStateOf((configuration.screenHeightDp / 2).dp) }
        val animatedOffsetY by animateDpAsState(targetValue = offsetY, animationSpec = tween(1000))

        // To move the view from bottom to top
        LaunchedEffect(Unit) {
            delay(1500)
            offsetY = -(configuration.screenHeightDp / 4 - 200).dp
            isConsumed = true
        }

        // To move the progress bar
        LaunchedEffect(Unit) {
            loadProgress {
                currentProgress = it
            }
        }
        // To delay the serving size visibility
        LaunchedEffect(Unit) {
            delay(25)
            showSize = true
        }

        if (currentProgress == 1.0f) {
            viewmodel.updateConsumptionAnimatedView(false)
            viewmodel.updateShowAppBar(true)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(SRTheme.colors.toastBackgroundColor)


        ) {
            // To Show the progress bar
            LinearProgressIndicator(
                progress = currentProgress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(
                        SRTheme.dimens.space5
                    ), color = Color.LightGray, trackColor = SRTheme.colors.trackColor
            )


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = animatedOffsetY)
                    .background(
                        SRTheme.colors.toastBackgroundColor
                    )
                    .padding(SRTheme.dimens.space5),

                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Box(
                        modifier = Modifier
                            .size(SRTheme.dimens.space150)
                            .clip(CircleShape)
                    ) {
                        AsyncImage(url = item.product.image, modifier = Modifier)
                    }

                    Column(
                        modifier = Modifier
                            .size(SRTheme.dimens.space60)
                            .align(Alignment.BottomCenter)
                    ) {
                        if (isConsumed.not()) {
                            AnimatedVisibility(
                                visible = showSize,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                val dullWhite1 = Color(0xFFF2F2F2)
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .offset(y = SRTheme.dimens.space20)
                                        .clip(CircleShape)
                                        .shadow(SRTheme.dimens.space2, CircleShape)
                                        .background(dullWhite1)
                                        .border(SRTheme.dimens.space3, Color.White, CircleShape)
                                ) {
                                    Text(
                                        text = item.product.regimen.servingSize.toString(),
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(
                                            fontFamily = FontFamily.Default,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            letterSpacing = 0.5.sp,
                                            color = Color.Black
                                        ),
                                        color = Color.Black,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .wrapContentSize(Alignment.Center)
                                    )

                                }
                            }
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.icon_tick),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .offset(y = SRTheme.dimens.space20)
                                    .clip(CircleShape)
                                    .shadow(SRTheme.dimens.space2, CircleShape)
                                    .border(SRTheme.dimens.space3, Color.White, CircleShape)
                            )
                        }

                    }


                }
                Spacer(modifier = Modifier.height(SRTheme.dimens.space30))
                val title: String = if (isConsumed.not()) item.product.name else {
                    "Got your '${item.product.name}' for today"
                }
                HeaderText(
                    text = title,
                    maxLines = 3,
                    color = SRTheme.colors.onPrimary,
                    textAlign = TextAlign.Center
                )
                if (isConsumed.not()) {
                    Spacer(modifier = Modifier.height(SRTheme.dimens.space10))
                    BodyText(
                        text = "Take '${item.product.regimen.servingSize}' ${item.product.regimen.servingUnit}!",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(SRTheme.dimens.space5))

                    BodyText(
                        text = item.product.regimen.description,
                        color = Color.White,
                        maxLines = 5,
                        textAlign = TextAlign.Center
                    )
                }
            }


        }
    }

    /** Iterate the progress value */
    suspend fun loadProgress(updateProgress: (Float) -> Unit) {
        for (i in 1..100) {
            updateProgress(i.toFloat() / 100)
            delay(50)
        }
    }

    @Composable
    fun SupplementItemView(item: Item, type: String) {
        Column(
            modifier = Modifier
                .background(Color.Transparent)
                .padding(SRTheme.dimens.space10),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(SRTheme.dimens.space10))
            Surface(
                Modifier
                    .width(SRTheme.dimens.space70)
                    .height(SRTheme.dimens.space140)
                    .shadow(SRTheme.dimens.space2, shape = RoundedCornerShape(40.dp))
                    .clip(RoundedCornerShape(SRTheme.dimens.space40))
                    .background(Color.White)

            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .width(SRTheme.dimens.space50)
                            .height(SRTheme.dimens.space110)

                    ) {
                        AsyncImage(
                            url = item.product.image, contentScale = ContentScale.FillHeight,
                            modifier = Modifier.fillMaxSize()

                        )
                    }
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .offset(y = -SRTheme.dimens.space5)
                            .shadow(SRTheme.dimens.space2, CircleShape)
                    ) {
                        val isConsumed = item.todayConsumption != null
                        Log.i("Pakka", "Product ${item.product.name} is Consumed : $isConsumed ")
                        SupplementActionView(
                            item = item,
                            isConsumed,
                            onAction = { action ->
                                if (action == TagUtils.addConsumption) {
                                    Log.i("Pakka", "Added Consumption")
                                    viewmodel.consumedSupplement(item, type, true)
                                } else {
                                    Log.i("Pakka", "Delete Consumption")
                                    viewmodel.deleteConsumptionTapped(
                                        currentItem = item,
                                        section = type
                                    )
                                }
                            })

                    }
                }
            }
            Log.i(
                "Pakka",
                "Item todayConsumption : ${if (item.todayConsumption != null) "Yes" else "No"}"
            )
            item.todayConsumption?.let {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    //${it.dosage} ${it.unit}
                    text = "${it.dosage} ${it.unit}", maxLines = 2, modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally), style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                        color = if (type == "NIGHT_TIME") SRTheme.colors.onPrimary else SRTheme.colors.contentPrimary
                    )
                )

                Text(
                    text = "${it.time?.let { it1 -> CommonUtils().convertTo12HourFormat(it1) }}",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.Normal,
                        fontSize = 10.sp,
                        lineHeight = 16.sp,
                        letterSpacing = 0.5.sp,
                        color = if (type == "NIGHT_TIME") SRTheme.colors.onPrimary else SRTheme.colors.contentPrimary
                    )
                )
            }
        }

    }
}
