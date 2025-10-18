package com.eillia.ehya.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.eillia.ehya.ui.utils.Dimens

@Composable
fun SearchOutlinedTextField(
  searchQuery: String,
  onValueChange: (String) -> Unit,
  onCleanSearch: () -> Unit,
) {
  OutlinedTextField(
    value = searchQuery,
    onValueChange = onValueChange,
    modifier = Modifier
      .fillMaxWidth()
      .padding(Dimens.PaddingSmall),
    placeholder = {
      Text(
        text = "ابحث في السنن...",
        style = MaterialTheme.typography.body1.copy(
          Color(0xFFAAAEB3)
        )
      )
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "بحث",
        tint = Color(0xFFAAAEB3)
      )
    },
    trailingIcon = {
      AnimatedVisibility(visible = searchQuery.isNotEmpty()) {
        Icon(
          imageVector = Icons.Default.Clear,
          contentDescription = "مسح",
          tint = Color(0xFFAAAEB3),
          modifier = Modifier.clickable { onCleanSearch() }
        )
      }
    },
    singleLine = true,
    textStyle = MaterialTheme.typography.body1.copy(
      Color(0xFFAAAEB3)
    ),
    colors = TextFieldDefaults.textFieldColors(
      backgroundColor = Color(0xFFD2AFB9).copy(alpha = 0.3f),
      focusedIndicatorColor = Color(0xFFD2AFB9).copy(alpha = 0.4f),
      unfocusedIndicatorColor = Color.Transparent
    ),
  )
}
