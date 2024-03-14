package com.stein.spacelunch.ui.upcoming_list.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stein.spacelunch.data.model.Upcoming
import com.stein.spacelunch.utils.toUpcomingString

@Composable
fun UpcomingItem(upcoming: Upcoming) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .height(220.dp),
            ) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = upcoming.image,
                    contentDescription = "Upcoming preview image",
                )
                Text(
                    modifier = Modifier
                        .width(200.dp)
                        .offset(x = (-30).dp, y = 26.dp)
                        .rotate(-45f)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(start = 20.dp, top = 4.dp, bottom = 4.dp, end = 20.dp),
                    text = upcoming.statusName,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = upcoming.name,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = upcoming.launchProvider,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = upcoming.podLocation,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = upcoming.windowEnd.toUpcomingString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}