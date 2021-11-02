package com.blackbyte.skucise.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.data.Amenity

@Composable
fun AmenityChip (amenity: Amenity) {
    Surface(shape = RoundedCornerShape(4.dp),
    modifier = Modifier.defaultMinSize(160.dp)) {
        Row(modifier = Modifier.padding(12.dp)){
            Icon(painter  = painterResource(id = amenity.iconDrawableID),
                contentDescription = amenity.text,
                modifier = Modifier
                    .size(22.dp))
            Spacer(modifier = Modifier.size(12.dp))
            Text(amenity.text)
        }
    }
}