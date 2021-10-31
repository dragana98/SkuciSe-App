package com.blackbyte.skucise.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import java.time.LocalDate
import androidx.compose.ui.input.pointer.pointerInput
import com.blackbyte.skucise.ui.theme.Green

private fun DayIndex(day: Int, month: Int, year: Int): Int {
    //val t: Array<Int> = arrayOf(0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
    val t: Array<Int> = arrayOf(6, 2, 1, 4, 6, 2, 4, 0, 3, 5, 1, 3)
    val d = day
    val m = month
    val y = year - (if (month < 3) 1 else 0)

    return (y + (y / 4) - (y / 100) + (y / 400) + t[m - 1] + d) % 7
}

private fun DaysInMonth(month: Int, year: Int): Int {
    return when (month) {
        1 -> 31
        2 -> if ((year % 400 == 0) or ((year % 4 == 0) and (year % 100 != 0))) 29 else 28
        3 -> 31
        4 -> 30
        5 -> 31
        6 -> 30
        7 -> 31
        8 -> 31
        9 -> 30
        10 -> 31
        11 -> 30
        12 -> 31

        else -> {
            -1
        }
    }
}

private fun MonthName(month: Int): String {
    return when (month) {
        1 -> "Januar"
        2 -> "Februar"
        3 -> "Mart"
        4 -> "April"
        5 -> "Maj"
        6 -> "Jun"
        7 -> "Jul"
        8 -> "Avgust"
        9 -> "Septembar"
        10 -> "Oktobar"
        11 -> "Novembar"
        12 -> "Decembar"
        else -> {
            "???"
        }
    }
}

enum class DatePickerMode {
    ALLOW_ALL,
    INACTIVATE_PAST_DAYS,
    INACTIVATE_UPCOMING_DAYS
}

private enum class DatePickerDayDecoration {
    REGULAR,
    DISABLED,
    HIGHLIGHTED
}

@Composable
private fun DayButton(
    value: Int,
    decoration: DatePickerDayDecoration = DatePickerDayDecoration.REGULAR,
    onPress: (() -> Int) -> Unit
) {
    var hasBorder by remember {
        mutableStateOf(false)
    }

    var persist by remember {
        mutableStateOf(value)
    }

    persist = value

    var reverseBorderStateAndReturnValue = fun(): Int {
        if (decoration != DatePickerDayDecoration.DISABLED) {
            hasBorder = !hasBorder
            return persist
        }
        return -1
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(
                width = 5.dp,
                shape = RoundedCornerShape(4.dp),
                color = Green.copy(alpha = if (hasBorder) 0.6f else 0.0f),
            )
            .size(46.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(shape = RoundedCornerShape(if (hasBorder) 0.dp else 4.dp))
                .background(
                    color =
                    when (decoration) {
                        DatePickerDayDecoration.DISABLED -> Color.Transparent
                        DatePickerDayDecoration.HIGHLIGHTED -> Green
                        else -> MaterialTheme.colors.surface
                    }
                )
                .size(36.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            onPress(reverseBorderStateAndReturnValue)
                        }
                    )
                }

        ) {
            Text(
                text = value.toString(),
                fontWeight = if (decoration == DatePickerDayDecoration.DISABLED) FontWeight.Normal else FontWeight.Medium,
                color =
                when (decoration) {
                    DatePickerDayDecoration.REGULAR -> MaterialTheme.colors.onSurface
                    DatePickerDayDecoration.DISABLED -> MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    DatePickerDayDecoration.HIGHLIGHTED -> Color.White
                    else -> MaterialTheme.colors.surface
                }
            )
        }
    }
}

/* Remove unnecessary args */

@Composable
fun DatePickerGrid(
    onDateSelected: (LocalDate?) -> Unit,
    date: LocalDate,
    highlighted: List<LocalDate> = listOf(),
    cutoffDate: LocalDate = LocalDate.now(),
    cutoffMode: DatePickerMode = DatePickerMode.ALLOW_ALL
) {
    val rows = 6
    val columns = 7

    var day: Int by remember {
        mutableStateOf(date.dayOfMonth)
    }
    var month: Int by remember {
        mutableStateOf(date.monthValue)
    }
    var year: Int by remember {
        mutableStateOf(date.year)
    }

    var prevMonthDayCount = DaysInMonth(
        month = if (month - 1 < 1) 12 else month - 1,
        year = if (month - 1 < 1) year - 1 else year
    )
    var currentMonthDayCount = DaysInMonth(
        month = month,
        year = year
    )
    var dayIndex = DayIndex(
        day = 1,
        month = month,
        year = year
    )

    if (dayIndex == 0) {
        dayIndex = 7
    }

    var disableMonthBackward = false
    var disableMonthForward = false

    var previousFunc by remember {
        mutableStateOf(fun() : Int { return -1 })
    }

    val execPreviousFuncAndRemember = fun(func: () -> Int) {
        previousFunc()
        val d = func()
        if(d > 0) {
            onDateSelected(LocalDate.of(year, month, d))
        } else {
            onDateSelected(null)
        }
        previousFunc = func
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.background(color = MaterialTheme.colors.background)
    ) {
        Column {
            Row {
                listOf("P", "U", "S", "Č", "P", "S", "N").map {
//              listOf("PON", "UTO", "SRE", "ČET", "PET", "SUB", "NED").map {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(46.dp)
                    ) {
                        Text(it)
                    }
                }
            }
            var calendarContents: IntArray =
                (((prevMonthDayCount - dayIndex + 1)..prevMonthDayCount) +
                        (1..currentMonthDayCount) + (1..((rows * columns) -
                        (dayIndex + currentMonthDayCount)))).toIntArray()

            var calendarDecorations: Array<DatePickerDayDecoration> =
                Array<DatePickerDayDecoration>(size = rows * columns) {
                    DatePickerDayDecoration.REGULAR
                }

            for (i in 0 until dayIndex) {
                calendarDecorations[i] = DatePickerDayDecoration.DISABLED
            }

            for (i in (dayIndex + currentMonthDayCount) until (rows * columns)) {
                calendarDecorations[i] = DatePickerDayDecoration.DISABLED
            }


            for (date in highlighted) {
                if ((date.monthValue == month) &&
                    (date.year == year)
                ) {
                    var pivotIndex = dayIndex + date.dayOfMonth - 1
                    calendarDecorations[pivotIndex] = DatePickerDayDecoration.HIGHLIGHTED
                }
            }

            if ((cutoffDate.monthValue == month) &&
                (cutoffDate.year == year)
            ) {
                var pivotIndex = dayIndex + cutoffDate.dayOfMonth - 1
                when (cutoffMode) {
                    DatePickerMode.INACTIVATE_PAST_DAYS -> {
                        disableMonthBackward = true
                        for (i in 0 until pivotIndex) {
                            calendarDecorations[i] = DatePickerDayDecoration.DISABLED
                        }
                    }
                    DatePickerMode.INACTIVATE_UPCOMING_DAYS -> {
                        disableMonthForward = true
                        for (i in (pivotIndex + 1) until (rows * columns)) {
                            calendarDecorations[i] = DatePickerDayDecoration.DISABLED
                        }
                    }
                    else -> {
                        disableMonthBackward = false
                        disableMonthForward = false
                    }
                }
            }

            Column(modifier = Modifier.width(IntrinsicSize.Min)) {
                for (t in 0 until rows) {
                    Row {
                        for (u in 0 until columns) {
                            DayButton(
                                value = calendarContents[t * columns + u],
                                decoration = calendarDecorations[t * columns + u],
                                onPress = execPreviousFuncAndRemember
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        enabled = !disableMonthBackward,
                        onClick = {
                            execPreviousFuncAndRemember(fun () : Int {return -1})
                            /* ORDER MATTERS */
                            year = if (month - 1 < 1) year - 1 else year
                            month = if (month - 1 < 1) 12 else month - 1
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "month backward"
                        )
                    }
                    OutlinedButton(
                        enabled = !disableMonthForward,
                        onClick = {
                            execPreviousFuncAndRemember(fun () : Int {return -1})
                            /* ORDER MATTERS */
                            year = if (month + 1 > 12) year + 1 else year
                            month = if (month + 1 > 12) 1 else month + 1
                        }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "month forward"
                        )
                    }
                }
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(year.toString(), fontWeight = FontWeight.Bold)
            Text(
                MonthName(month),
                style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    SkuciSeTheme {
        Scaffold(
            backgroundColor = MaterialTheme.colors.background,
            topBar = { NavTopBar("Odabir datuma") },
        ) {
            var pickedDate by remember {
                mutableStateOf(LocalDate.now())
            }
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()) {
                Column {
                    DatePickerGrid(
                        date = LocalDate.of(2021, 11, 14),
                        highlighted = listOf(
                            LocalDate.of(2021, 11, 6),
                            LocalDate.of(2021, 11, 7),
                            LocalDate.of(2021, 11, 12)
                        ),
                        onDateSelected = { t : LocalDate? -> pickedDate = t}
                    )
                    if(pickedDate != null) {
                        Text("Izabrali ste datum: ${pickedDate.dayOfMonth}.${pickedDate.monthValue}.${pickedDate.year}.")
                    } else {
                        Text("Izaberite datum.")
                    }

                }
            }
        }
    }
}
