package com.android.tyzen.xwalletwise.ui.activity.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.ui.fragment.CategoryBottomSheet
import com.android.tyzen.xwalletwise.ui.fragment.DateTimePicker
import com.android.tyzen.xwalletwise.ui.fragment.FAButton
import com.android.tyzen.xwalletwise.ui.fragment.FormTextField
import com.android.tyzen.xwalletwise.ui.fragment.TransactionTypeChipsGroup
import com.android.tyzen.xwalletwise.ui.fragment.WalletWiseTopAppBar
import com.android.tyzen.xwalletwise.ui.viewmodel.transaction.TransactionDetailUiState
import com.android.tyzen.xwalletwise.ui.viewmodel.transaction.TransactionDetailViewModel
import com.android.tyzen.xwalletwise.util.categoryIconsList
import com.android.tyzen.xwalletwise.util.formatBalance
import com.android.tyzen.xwalletwise.util.formatDate
import com.android.tyzen.xwalletwise.util.formatDouble

/**
 * Input Transaction Screens =======================================================================
 */
@Composable
fun InputTransactionScreen(
    transactionDetailViewModel: TransactionDetailViewModel,
    onBackClick: () -> Unit, )
{
    val transactionId = transactionDetailViewModel.transactionId
    val transactionUiState = transactionDetailViewModel.transactionDetailUiState

    //val ocrTransactionViewModel = viewModel<OCRTransactionViewModel>(factory = OCRTransactionViewModelFactor())
    //val ocrTransactionUiState = ocrTransactionViewModel.ocrTransactionUiState

    Scaffold(
        topBar = {
            WalletWiseTopAppBar(
                title = "Add new Transaction",
                useIconForTitle = false,
                showNavigationButton = true,
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationClick = onBackClick,
                showActionButton = false,
            )
        },
        floatingActionButton = {
            FAButton(
                modifier = Modifier,
                onClick = {
                    transactionDetailViewModel.addTransaction()
                    onBackClick()
                },
                icon = Icons.Default.Check,
                text = "Save Change",
                contentDescription = "Save Category")
        },
        floatingActionButtonPosition = FabPosition.Center,
    )
    {innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        when(transactionId.toInt()) {
//            -2 -> OCRTransactionScreen(
//                        modifier = modifier,
//                        transactionDetailViewModel = transactionDetailViewModel,
//                        transactionDetailUiState = transactionUiState,
//                        ocrTransactionViewModel = ocrTransactionViewModel,
//                        ocrTransactionUiState = ocrTransactionUiState,
//                    )
//            -3 -> TextTransactionScreen()
            else -> ManualTransactionScreen(
                modifier = modifier,
                transactionDetailViewModel = transactionDetailViewModel,
                transactionDetailUiState = transactionUiState,
            )
        }
    }
}

//Manual Transaction Screen ========================================================================
@Composable
fun ManualTransactionScreen(
    modifier: Modifier,
    transactionDetailViewModel: TransactionDetailViewModel,
    transactionDetailUiState: TransactionDetailUiState,)
{
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDateTimePicker by remember { mutableStateOf(false) }

    Box(modifier = modifier,)
    {
        Column(
            modifier = Modifier
                .padding(24.dp), )
        {
            /**
             * TRANSACTION TYPE
             */
            /**
             * TRANSACTION TYPE
             */
            /**
             * TRANSACTION TYPE
             */

            /**
             * TRANSACTION TYPE
             */
            TransactionTypeChipsGroup(
                readOnly = false,
                initialType = transactionDetailUiState.transactionType,
                onTransactionTypeSet = {
                    transactionDetailViewModel.onTransactionTypeChanged(it)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))

            /**
             * CATEGORY & TRANSACTION NAME
             */

            /**
             * CATEGORY & TRANSACTION NAME
             */

            /**
             * CATEGORY & TRANSACTION NAME
             */

            /**
             * CATEGORY & TRANSACTION NAME
             */
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween, )
            {
                IconButton(
                    onClick = { showBottomSheet = true },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        ),)
                {
                    Image(
                        painter = painterResource(id = categoryIconsList[transactionDetailUiState.category.icon] ?: R.drawable.ic_category_other),
                        contentDescription = "Category Icon: ${transactionDetailUiState.category.icon}",
                    )
                }

                Spacer(modifier = Modifier.weight(0.03f))

                Box(
                    modifier = Modifier.weight(0.87f), )
                {
                    FormTextField(
                        label = "Transaction Name",
                        value = transactionDetailUiState.transactionTitle,
                        onValueChange = { transactionDetailViewModel.onTransactionTitleChanged(it) },
                        readOnly = false,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //AMOUNT
            FormTextField(
                label = "Amount",
                value = formatBalance(transactionDetailUiState.transactionAmount*1.0),
                onValueChange = { transactionDetailViewModel.onTransactionAmountChanged(formatDouble(it))},
                readOnly = false,
            )
            Spacer(modifier = Modifier.height(16.dp))

            //DATE & TIME
            FormTextField(
                label = "Date & Time",
                value = formatDate(transactionDetailUiState.transactionDate),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showDateTimePicker = true
                        }
                    ) {
                        Icon(Icons.Filled.DateRange, contentDescription = "Select Date and Time")
                    }
                },
                onValueChange = {},
                readOnly = false,
            )

            if (showDateTimePicker)
            {
                DateTimePicker(
                    onDateTimeSelected = {
                        transactionDetailViewModel.onTransactionDateChanged(it)
                        showDateTimePicker = false
                    },
                    initialDate = transactionDetailUiState.transactionDate,
                )
            }

            //DESCRIPTION
            FormTextField(
                label = "Description",
                value = transactionDetailUiState.transactionDescription ?: "",
                onValueChange = { transactionDetailViewModel.onTransactionDescriptionChanged(it) },
                readOnly = false,
            )
        }

        if (showBottomSheet)
        {
            CategoryBottomSheet(
                categories = transactionDetailUiState.categories,
                onCategorySelected = {
                    transactionDetailViewModel.onCategoryIdChanged(it)
                    showBottomSheet = false
                },
                onDismissRequest = { showBottomSheet = false },
            )
        }
    }
}

//OCR Transaction Screen ===========================================================================
//@Composable
//fun OCRTransactionScreen(
//    modifier: Modifier,
//    transactionDetailViewModel: TransactionDetailViewModel,
//    transactionDetailUiState: TransactionDetailUiState,
//    ocrTransactionViewModel: OCRTransactionViewModel,
//    ocrTransactionUiState: OcrTransactionUiState, )
//{
//    val context = LocalContext.current
//    val categoriesList = transactionDetailUiState.categories
//    var imageBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }
//
//    Scaffold(
//        floatingActionButton = {
//            FAButton(
//                modifier = Modifier,
//                onClick = {
//                    transactionDetailViewModel.onTransactionTypeChanged(TransactionType.EXPENSE)
//                },
//                icon = Icons.Default.KeyboardArrowUp,
//                contentDescription = "Get Data",
//            )
//        },
//        floatingActionButtonPosition = FabPosition.Start,
//    ) {innerPadding ->
//        ManualTransactionScreen(
//            modifier = modifier.padding(innerPadding),
//            transactionDetailViewModel = transactionDetailViewModel,
//            transactionDetailUiState = transactionDetailUiState,
//        )
//    }
//
//    /**
//     * CAPTURE RECEIPT -----------------------------------------------------------------------------
//     */
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicturePreview(), )
//    { newImage ->
//        imageBitmap = newImage
//    }
//
//    val permissionCheckResult = ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
//
//    LaunchedEffect(key1 = permissionCheckResult)
//    {
//        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED)
//        {
//            cameraLauncher.launch()
//        }
//        else
//        {
//            Toast.makeText(context, "Don't have camera permission, change to Manual mode", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    /**
//     * OCR PROCESS----------------------------------------------------------------------------------
//     */
//    LaunchedEffect(imageBitmap)
//    {
//        imageBitmap?.let { ocrTransactionViewModel.onImageCaptured(it, categoriesList) }
//    }
//
//    LaunchedEffect(ocrTransactionUiState.ocrTransaction != null)
//    {
//        ocrTransactionUiState.ocrTransaction?.let {
//            transactionDetailViewModel.onTransactionTitleChanged(it.transactionTitle)
//            Log.d("OCR", "OCR: ${it.transactionTitle}")
//            transactionDetailViewModel.onTransactionAmountChanged(it.amount)
//            Log.d("OCR", "OCR: ${it.amount}")
//            transactionDetailViewModel.onTransactionDescriptionChanged(it.description ?: "")
//            Log.d("OCR", "OCR: ${it.description}")
//            if (it.categoryId != -1)
//            {
//                transactionDetailViewModel.onCategoryIdChanged(it.categoryId)
//                Log.d("OCR", "OCR: ${it.categoryId}")
//            }
//
//            val datetimeString = it.datetime
//            var parsedDate: Date? = null
//            if (datetimeString.isNotBlank() && datetimeString.isNotEmpty())
//            {
//                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
//                parsedDate = try {
//                    formatter.parse(datetimeString)
//                } catch (e: ParseException) {
//                    Log.e("OcrTransactionParseDatetime", "Error parsing date: ${e.message}") // Log the error
//                    null
//                }
//            }
//            transactionDetailViewModel.onTransactionDateChanged(parsedDate ?: Date())
//        }
//    }
//}

//Text Transaction Screen ==========================================================================
//@Composable
//fun TextTransactionScreen()
//{
//
//}