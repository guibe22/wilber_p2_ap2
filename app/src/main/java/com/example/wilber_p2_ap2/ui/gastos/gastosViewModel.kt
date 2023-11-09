package com.example.wilber_p2_ap2.ui.gastos

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wilber_p2_ap2.data.remote.dto.gastosDto
import com.example.wilber_p2_ap2.data.repository.gastosRepository
import com.example.wilber_p2_ap2.util.Resource
import com.example.wilber_p2_ap2.util.gastosListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class gastosViewModel @Inject constructor(
    private val repository: gastosRepository
) : ViewModel() {
    var fecha by mutableStateOf("")
    var idSuplidor  by mutableStateOf("")
    var suplidor by mutableStateOf("")
    var concepto by mutableStateOf("")
    var nfc by mutableStateOf("")
    var itbis by mutableStateOf("")
    var monto by mutableStateOf("")
    var id by mutableStateOf(0)

    var fechaError by mutableStateOf(false)
    var idSuplidorError by mutableStateOf(false)
    var conceptoError by mutableStateOf(false)
    var nfcError by mutableStateOf(false)
    var itbisError by mutableStateOf(false)
    var montoError by mutableStateOf(false)

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()
    var mensaje by mutableStateOf("")

     private var _state = mutableStateOf(gastosListState())
    val state: State<gastosListState> = _state

    fun onFechaChange(valor:String){
        fecha=valor
        fechaError= valor.isNullOrBlank()
    }
    fun onIdSuplidorChange(valor:String){
        idSuplidor= valor
        idSuplidorError= valor.isNullOrBlank()
    }
    fun onConceptoChange(valor:String){
        concepto= valor
        conceptoError= valor.isNullOrBlank()
    }
    fun onNfcChange(valor:String){
        nfc = valor
        nfcError= valor.isNullOrBlank()
    }
    fun onItbisChange(valor:String){
        itbis= valor
        itbisError= valor.isNullOrBlank()
    }
    fun onMontoChange(valor:String){
        monto = valor
        montoError= valor.isNullOrBlank()
    }
    fun Cargar(){
        repository.getGastos().onEach{ result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = gastosListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = gastosListState(Gastos = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = gastosListState(error = result.message ?: "Error desconocido")
                }
            }
        }.launchIn(viewModelScope)
    }
    init {
        Cargar()
    }

    fun saveGastos() {
            viewModelScope.launch {

                if (!validar()) {
                    val gastos = gastosDto(
                        idGasto = id,
                        fecha=fecha,
                        idSuplidor= idSuplidor.toIntOrNull() ?:0,
                        suplidor=null,
                        concepto=concepto,
                        ncf = nfc.toIntOrNull() ?:0,
                        itbis = itbis.toIntOrNull() ?:0,
                        monto = monto.toIntOrNull()?:0
                    )

                    if (id!=0) repository.putGastos(id,gastos)   else repository.postGastos(gastos)

                    mensaje="guardado correctamente"
                    limpiar()
                    id=0
                } else {
                    mensaje="error"
                }
            }
        }

    fun deleteGastos(id: Int){
        viewModelScope.launch {
            repository.deleteGastos(id)
        }
    }

    fun validar(): Boolean {
        onFechaChange(fecha)
        onIdSuplidorChange(idSuplidor)
        onConceptoChange(concepto)
        onNfcChange(nfc)
        onItbisChange(itbis)
        onMontoChange(monto)
        return fechaError || idSuplidorError || conceptoError || nfcError ||  itbisError || montoError
    }
    fun limpiar(){
         fecha =""
         idSuplidor =""
         suplidor=""
         concepto =""
         nfc =""
         itbis =""
         monto =""
    }
    fun Modificar( gastos:gastosDto){
        id= gastos.idGasto!!
        fecha = gastos.fecha
        idSuplidor =gastos.idSuplidor.toString()
        suplidor=gastos.suplidor.toString()
        concepto =gastos.concepto
        nfc =gastos.ncf.toString()
        itbis= gastos.itbis.toString()
        monto =gastos.monto.toString()
    }

}
