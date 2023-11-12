package com.example.wilber_p2_ap2.ui.gastos

import android.app.DatePickerDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderColor
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.wilber_p2_ap2.data.remote.dto.gastosDto
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun gastosScreen(
    viewModel: gastosViewModel = hiltViewModel()
){
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = viewModel.mensaje,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro Gastos") }
            )
        }
    ) {
           Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)

        ) {
               LazyColumn(
                   modifier = Modifier
                       .fillMaxWidth()
               ) {
                   item {
                       RegistroGasto(ViewModel = viewModel)
                   }

                   when {
                       uiState.isLoading -> {
                           item{
                               Box(
                                   contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
                               ) {
                                   CircularProgressIndicator()
                               }
                           }
                       }

                       uiState.Gastos.isNotEmpty() -> {
                           items(uiState.Gastos){gasto->
                               Gastoscard(gasto,viewModel)
                           }
                       }
                       !uiState.error.isNullOrBlank()-> {
                           item{
                               Text(text = "error:"+ uiState.error)
                           }

                       }
                   }



               }

        }
    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegistroGasto(
    ViewModel: gastosViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {


        val context = LocalContext.current
        DateTextField(viewModel = ViewModel, context =context )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = ViewModel.idSuplidor,
                label = { Text(text = "Id Suplidor") },
                singleLine = true,
                onValueChange = ViewModel::onIdSuplidorChange,
                isError = ViewModel.idSuplidorError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = ViewModel.concepto,
                label = { Text(text = "Concepto") },
                singleLine = true,
                onValueChange = ViewModel::onConceptoChange,
                isError = ViewModel.conceptoError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = ViewModel.ncf,
                label = { Text(text = "Ncf") },
                singleLine = true,
                onValueChange = ViewModel::onNcfChange,
                isError = ViewModel.ncfError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = ViewModel.itbis,
                label = { Text(text = "Itbis") },
                singleLine = true,
                onValueChange = ViewModel::onItbisChange,
                isError = ViewModel.itbisError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = ViewModel.monto,
                label = { Text(text = "Monto") },
                singleLine = true,
                onValueChange = ViewModel::onMontoChange,
                isError = ViewModel.montoError,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
        }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                keyboardController?.hide()
                ViewModel.saveGastos()

            })
        {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "guardar")
            Text(text = "Guardar")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DateTextField(
    viewModel: gastosViewModel,
    context: Context
) {
    val year: Int
    val month: Int
    val day: Int
    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    var isDatePickerVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = "Fecha") },
        singleLine = true,
        maxLines = 1,
        value = viewModel.fecha,
        onValueChange = viewModel::onFechaChange,
        leadingIcon = {
            IconButton(
                onClick = {
                    keyboardController?.hide()
                    isDatePickerVisible = true
                }
            ) {
                Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Calendario")
            }
        },
        isError = viewModel.fechaError,
        readOnly = true
    )

    if (isDatePickerVisible) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    .format(Date(year - 1900, month, dayOfMonth))
                viewModel.fecha = formattedDate
                isDatePickerVisible = false
            },
            year,
            month,
            day
        )

        DisposableEffect(Unit) {
            datePickerDialog.show()
            onDispose {
                datePickerDialog.dismiss()
            }
        }

        datePickerDialog.setOnCancelListener { isDatePickerVisible = false }
        datePickerDialog.setOnDismissListener { isDatePickerVisible = false }
    }
}


@Composable
fun Gastoscard(
    gastos: gastosDto,
    viewModel:gastosViewModel
    ){
    val fechaParseada = LocalDateTime.parse(gastos.fecha, DateTimeFormatter.ISO_DATE_TIME)
    val fechaFormateada = fechaParseada.format(DateTimeFormatter.ISO_DATE)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Id: " + gastos.idGasto.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                        .weight(1f)

                )
                Text(
                    text =fechaFormateada,
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    textAlign= TextAlign.End,
                    modifier = Modifier
                        .weight(1f)

                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = gastos.suplidor.toString(),
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = gastos.concepto,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row (

            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(2f)
                ) {
                    Text(
                        text = "NCF:",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign= TextAlign.End
                    )
                    Text(
                        text = "ITBS:",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign= TextAlign.End
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(4f)
                ) {
                    Text(
                        text = gastos.ncf.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1

                    )
                    Text(
                        text = gastos.itbis.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(3f)
                ) {
                    Text(
                        text = "$ " + gastos.monto.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        textAlign = TextAlign.Right,

                        )
                }

            }


        }
        Divider( thickness = 1.dp, color = Color.Black)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )  {
            Row(

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(2f)
                ){
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors =  ButtonDefaults.buttonColors(Color.Blue),
                        onClick = {
                            viewModel.Modificar(gastos)

                        })
                    {
                        Icon(imageVector = Icons.Default.BorderColor, contentDescription = "guardar")
                        Text(text = "Editar")
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .weight(2f)
                ){
                    OutlinedButton(
                        modifier = Modifier.fillMaxWidth(),
                        colors =  ButtonDefaults.buttonColors(Color.Red),
                        onClick = {
                            gastos.idGasto?.let { viewModel. deleteGastos(it.toInt()) }
                        })

                    {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "guardar")
                        Text(text = "Eliminar")
                    }
                }




            }

        }


    }

}