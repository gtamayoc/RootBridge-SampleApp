package com.rootbridge.sampleapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rootbridge.sampleapp.viewmodel.MainViewModel

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    // Collect the polled value — updates automatically when RAM is modified
    val coins by viewModel.coinsDisplay.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // ── App title ──────────────────────────────────────────────────
        Text(
            text = "RootBridge Sample Target",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "RAM Analysis Target App",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // ── Main value display — big and easy to read ──────────────────
        Text(
            text = "Coins",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = "$coins",
            fontSize = 72.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        // ── Action buttons ─────────────────────────────────────────────
        Button(
            onClick = { viewModel.addOne() },
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(52.dp)
        ) {
            Text(text = "+1 Coins", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { viewModel.addTen() },
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(52.dp)
        ) {
            Text(text = "+10 Coins", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { viewModel.reset() },
            modifier = Modifier
                .fillMaxWidth(0.65f)
                .height(52.dp)
        ) {
            Text(text = "Reset", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // ── Status hint ────────────────────────────────────────────────
        Text(
            text = "UI refreshes every 100ms\nExternal memory writes are reflected automatically",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}
