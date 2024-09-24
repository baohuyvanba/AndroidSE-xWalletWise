package com.android.tyzen.xwalletwise.ui.activity.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.tyzen.xwalletwise.R
import com.android.tyzen.xwalletwise.model.transaction.TransactionType
import com.android.tyzen.xwalletwise.model.user.UserPreferences
import com.android.tyzen.xwalletwise.ui.fragment.BalanceAmountField
import com.android.tyzen.xwalletwise.ui.fragment.CategoryBottomSheet
import com.android.tyzen.xwalletwise.ui.fragment.DateTimePicker
import com.android.tyzen.xwalletwise.ui.fragment.FABViewDetailExtended
import com.android.tyzen.xwalletwise.ui.fragment.FABViewDetailIcon
import com.android.tyzen.xwalletwise.ui.fragment.FAButton
import com.android.tyzen.xwalletwise.ui.fragment.FormTextField
import com.android.tyzen.xwalletwise.ui.fragment.TransactionDetailField
import com.android.tyzen.xwalletwise.ui.fragment.TransactionTypeChipsGroup
import com.android.tyzen.xwalletwise.ui.fragment.WalletWiseTopAppBar
import com.android.tyzen.xwalletwise.ui.fragment.WalletWiseViewDetailTopAppBar
import com.android.tyzen.xwalletwise.ui.viewmodel.transaction.TransactionDetailViewModel
import com.android.tyzen.xwalletwise.util.categoryIconsList
import com.android.tyzen.xwalletwise.util.formatBalance
import com.android.tyzen.xwalletwise.util.formatDate
import com.android.tyzen.xwalletwise.util.formatDouble
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun TransactionDetailScreen(
    onBackClick: () -> Unit, )
{
    val transactionDetailViewModel: TransactionDetailViewModel = hiltViewModel()

    val transactionId = transactionDetailViewModel.transactionId
    val viewMode by remember { mutableStateOf(transactionId > 0) }

    when (viewMode) {
        //View - Edit Mode
        true  -> ViewTransactionScreen(
                    transactionDetailViewModel = transactionDetailViewModel,
                    onBackClick = onBackClick,
        )
        //Input Mode
        false -> InputTransactionScreen(
                    transactionDetailViewModel = transactionDetailViewModel,
                    onBackClick = onBackClick,
        )
    }
}


/**
 * View - Edit Transaction =========================================================================
 */
@Composable
fun ViewTransactionScreen(
    transactionDetailViewModel: TransactionDetailViewModel,
    userPreferences: UserPreferences = hiltViewModel(),
    onBackClick: () -> Unit, )
{
    //Ui State
    val transactionId = transactionDetailViewModel.transactionId
    val transactionDetailUiState = transactionDetailViewModel.transactionDetailUiState
    //Screen Size
    val configuration = LocalConfiguration.current
    val screenHeight  = configuration.screenHeightDp
    //Currency
    val scope = rememberCoroutineScope()
    var currency by rememberSaveable { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            currency = userPreferences.currency.first()
        }
    }

    var readOnly by remember { mutableStateOf(true) }
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDateTimePicker by remember { mutableStateOf(false) }

    val backgroundColor: List<Color>
    val balanceColor: Color
    if (transactionDetailUiState.transactionType == TransactionType.INCOME) {
        backgroundColor = listOf(
            Color(0xFFFAFFD1),
            Color(0xFFA1FFCE)
        )
        balanceColor = Color(0xFF59C173)
    } else { //TransactionType.OUTCOME
        backgroundColor = listOf(
            Color(0xFFe1eec3).copy(0.5f),
            Color(0xFFf05053).copy(0.6f)
        )
        balanceColor = Color(0xFFfd746c).copy(0.8f)
    }

    Scaffold(
        topBar = {
            WalletWiseViewDetailTopAppBar(
                title = "Your Transaction",
                onNavigationClick = onBackClick,
            )
        },
        floatingActionButton = {
            if (readOnly) {
                FABViewDetailIcon(
                    modifier = Modifier.padding(
                        bottom = (screenHeight*0.02).dp,
                        end    = (screenHeight*0.02).dp
                    ),
                    onClick = {
                        readOnly = false
                    },
                    icon = Icons.Default.Edit,
                    containerColor = Color.White.copy(0.2f),
                    shape = FloatingActionButtonDefaults.shape,
                    iconColor = Color.White,
                    contentDescription = "Edit Transaction",
                )
            }
            else {
                FABViewDetailExtended(
                    modifier = Modifier.padding(
                        bottom = (screenHeight*0.02).dp,
                        end    = (screenHeight*0.02).dp
                    ),
                    onClick = {
                        transactionDetailViewModel.updateTransaction(transactionId)
                        readOnly = true
                    },
                    text = "Save Change",
                    icon = Icons.Default.Check,
                    containerColor = Color.White.copy(0.2f),
                    shape = FloatingActionButtonDefaults.extendedFabShape,
                    contentColor = Color.White,
                    contentDescription = "Save Change",
                )
            }
        },)
    { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            backgroundColor[0],
                            backgroundColor[1],
                        )
                    )
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),)
            {
                //BALANCE AMOUNT -------------------------------------------------------------------
                BalanceAmountField(
                    value = formatBalance(transactionDetailUiState.transactionAmount * 1.0),
                    onValueChange = {
                        transactionDetailViewModel.onTransactionAmountChanged(formatDouble(it))
                    },
                    readOnly = readOnly,
                    balanceColor = balanceColor,
                    currency = currency,
                )
                Spacer(modifier = Modifier.height(16.dp))

                //TRANSACTION TYPE -----------------------------------------------------------------
                TransactionTypeChipsGroup(
                    readOnly = readOnly,
                    initialType = transactionDetailUiState.transactionType,
                    onTransactionTypeSet = {
                        if (!readOnly) transactionDetailViewModel.onTransactionTypeChanged(it)
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * TRANSACTION TITLE ===============================================================
                 */
                TransactionDetailField(
                    label = "Title",
                    value = transactionDetailUiState.transactionTitle,
                    onValueChange = {
                        transactionDetailViewModel.onTransactionTitleChanged(it)
                    },
                    modifier = Modifier,
                    keyboardOptions = KeyboardOptions.Default,
                    readOnly = readOnly,
                )
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * CATEGORY ========================================================================
                 */
                IconButton(
                    onClick = { if (!readOnly) showBottomSheet = true },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = CircleShape
                        ),
                ) {
                    Image(
                        painter = painterResource(
                            id = categoryIconsList[transactionDetailUiState.category.icon]
                                ?: R.drawable.ic_category_other
                        ),
                        contentDescription = "Category Icon: ${transactionDetailUiState.category.icon}",
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                /**
                 * DATE & TIME =====================================================================
                 */
                TransactionDetailField(
                    label = "Date & Time",
                    value = formatDate(transactionDetailUiState.transactionDate),
                    trailingIcon = {
                        IconButton(
                            onClick = { if (!readOnly) showDateTimePicker = true }
                        ) {
                            Icon(
                                Icons.Filled.DateRange,
                                contentDescription = "Select Date and Time",
                            )
                        }
                    },
                    onValueChange = {},
                    readOnly = readOnly,
                )

                //Date and Time Picker
                if (showDateTimePicker) {
                    DateTimePicker(
                        onDateTimeSelected = {
                            transactionDetailViewModel.onTransactionDateChanged(it)
                            showDateTimePicker = false
                        },
                        initialDate = transactionDetailUiState.transactionDate,
                    )
                }

                /**
                 * DESCRIPTION =====================================================================
                 */
                TransactionDetailField(
                    label = "Description",
                    value = transactionDetailUiState.transactionDescription ?: "",
                    onValueChange = {
                        transactionDetailViewModel.onTransactionDescriptionChanged(it)
                    },
                    readOnly = readOnly,
                )
            }

            //Bottom Sheet
            if (showBottomSheet) {
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
}