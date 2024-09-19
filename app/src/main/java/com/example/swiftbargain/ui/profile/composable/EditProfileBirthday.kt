package com.example.swiftbargain.ui.profile.composable

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.swiftbargain.R
import com.example.swiftbargain.ui.theme.colors
import com.example.swiftbargain.ui.theme.spacing
import java.util.Calendar

@Composable
fun EditProfileBirthday(
    modifier: Modifier = Modifier,
    birthday: String,
    onSelectDate: (String) -> Unit
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            onSelectDate("$selectedYear-${selectedMonth + 1}-$selectedDay")
        }, year, month, day
    )

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.space12)
    ) {
        Text(
            text = stringResource(R.string.birthday),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colors.text
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 48.dp, max = 56.dp)
                .clickable(
                    onClick = { datePicker.show() },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(color = MaterialTheme.colors.textLight)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = birthday.ifEmpty { "$day-$month-$year" },
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colors.textGrey
            )
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_calender),
                contentDescription = null,
                tint = MaterialTheme.colors.textGrey
            )
        }
    }

}
