package com.example.avaliativa2

import androidx.compose.ui.Modifier



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp

data class Player(
    var name: String = "",
    var level: Int = 1,
    var equipmentBonus: Int = 0,
    var modifiers: Int = 0
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayerProgressApp()
        }
    }
}

@Composable
fun PlayerProgressApp() {
    // Usando um estado para gerenciar a lista de jogadores
    val players = remember { mutableStateListOf<Player>().apply { repeat(6) { add(Player()) } } }

    Column(modifier = Modifier.padding(16.dp)) {
        for (index in players.indices) {
            PlayerCard(index, players)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerCard(index: Int, players: MutableList<Player>) {
    val player = players[index]

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            TextField(
                value = player.name,
                onValueChange = { players[index] = player.copy(name = it) },
                label = { Text("Nome do Jogador") }
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Nível: ${player.level}")
                IconButton(onClick = {
                    if (player.level < 10) players[index] = player.copy(level = player.level + 1)
                }) {
                    Text("+")
                }
                IconButton(onClick = {
                    if (player.level > 1) players[index] = player.copy(level = player.level - 1)
                }) {
                    Text("-")
                }
            }
            Text("Poder Total: ${calculateTotalPower(player)}")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Bônus de Equipamento: ${player.equipmentBonus}")
                IconButton(onClick = {
                    players[index] = player.copy(equipmentBonus = player.equipmentBonus + 1)
                }) {
                    Text("+")
                }
                IconButton(onClick = {
                    players[index] = player.copy(equipmentBonus = player.equipmentBonus - 1)
                }) {
                    Text("-")
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Modificadores: ${player.modifiers}")
                IconButton(onClick = {
                    players[index] = player.copy(modifiers = player.modifiers + 1)
                }) {
                    Text("+")
                }
                IconButton(onClick = {
                    players[index] = player.copy(modifiers = player.modifiers - 1)
                }) {
                    Text("-")
                }
            }
        }
    }
}

fun calculateTotalPower(player: Player): Int {
    return player.level + player.equipmentBonus + player.modifiers
}
