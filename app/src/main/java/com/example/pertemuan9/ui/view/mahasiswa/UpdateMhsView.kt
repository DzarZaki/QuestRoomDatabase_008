package com.example.pertemuan9.ui.view.mahasiswa

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pertemuan9.ui.costumwidget.TopAppBar
import com.example.pertemuan9.ui.viewmodel.PenyediaViewModel
import com.example.pertemuan9.ui.viewmodel.UpdateMhsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateMhsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.updateUIState // Ambil UI state dari ViewModel
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackBarMessage
    LaunchedEffect(uiState.snackBarMassage) {
        uiState.snackBarMassage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()  // Reset snackbar message
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                onBack = onBack,
                judul = "Edit Mahasiswa",
                showBackButton = true
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Isi body form untuk update data
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = { updateEvent ->
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()) {
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main) {
                                onNavigate() // Navigasi di main thread
                            }
                        } else {
                            // Tampilkan error jika validasi gagal
                            snackbarHostState.showSnackbar("Field tidak valid!")
                        }
                    }
                }
            )
        }
    }
}