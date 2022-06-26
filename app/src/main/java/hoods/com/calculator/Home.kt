package hoods.com.calculator

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import hoods.com.calculator.ui.theme.CalculatorTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeCalculator(calculatorViewModel: CalculatorViewModel?) {
    val uiState = calculatorViewModel?.uiState
    Box(modifier = Modifier.fillMaxSize()) {
        val contentMargin = 16.dp













        Column(modifier = Modifier.align(Alignment.BottomCenter)) {

            Column(
                modifier = Modifier
                    .padding(horizontal = contentMargin)
                    .align(Alignment.End)
            ) {
                Text(
                    text = uiState?.infix ?: "-20+60/-5*(20-5)",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.size(contentMargin))
                Text(
                    text = uiState?.result ?: "-200",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.size(contentMargin))


            }

            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.size(contentMargin))

                CharacterItem(
                    char = "(",
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f, true),
                ) {
                    calculatorViewModel?.onInfixChange("(")
                }
                Spacer(modifier = Modifier.size(contentMargin))
                CharacterItem(
                    char = ")",
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f, true),
                ) {
                    calculatorViewModel?.onInfixChange(")")
                }
                Spacer(modifier = Modifier.size(contentMargin))


            }
            Spacer(modifier = Modifier.size(contentMargin))

            Row {
                val numbers = listOf(
                    "7", "8", "9", "4", "5",
                    "6", "1", "2", "3", "0",
                    ".", "C"
                )


                LazyVerticalGrid(
                    cells = GridCells.Fixed(3),
                    modifier = Modifier.weight(1f)
                ) {
                    items(numbers) { number ->

                        CharacterItem(
                            char = number,
                            modifier = Modifier.padding(contentMargin),

                            ) {
                            if (number != "C") {
                                calculatorViewModel?.onInfixChange(number)
                            } else {
                                calculatorViewModel?.clearInfixExpression()
                            }

                        }


                    }


                }

            ConstraintLayout(modifier = Modifier.weight(.8f)) {
            val (addition, multiplication,
                division, minus, power, equal) = createRefs()
                CharacterItem(
                    char = "-",
                    modifier = Modifier
                        .height(50.dp)
                        .constrainAs(minus){
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(division.start)
                            bottom.linkTo(addition.top)
                        }
                        .aspectRatio(1f)
                    ,
                    color = MaterialTheme.colors.secondary
                ) {
                    calculatorViewModel?.onInfixChange("-")
                }
                CharacterItem(
                    char = "/",
                    modifier = Modifier
                        .height(50.dp)
                        .constrainAs(division){
                            top.linkTo(parent.top)
                            start.linkTo(minus.end,contentMargin)
                            end.linkTo(parent.end,contentMargin)
                        }
                        .aspectRatio(1f)
                    ,
                    color = MaterialTheme.colors.secondary
                ) {
                    calculatorViewModel?.onInfixChange("/")
                }

                CharacterItem(
                    char = "*",
                    modifier = Modifier
                        .height(50.dp)
                        .constrainAs(multiplication){
                            top.linkTo(division.bottom,contentMargin)
                            start.linkTo(addition.end)
                            end.linkTo(parent.end)
                            bottom.linkTo(power.top)
                        }
                        .aspectRatio(1f)
                    ,
                    color = MaterialTheme.colors.secondary
                ) {
                    calculatorViewModel?.onInfixChange("*")
                }
                CharacterItem(
                    char = "^",
                    modifier = Modifier
                        .height(50.dp)
                        .constrainAs(power){
                            top.linkTo(multiplication.bottom,contentMargin)
                            start.linkTo(addition.end,contentMargin)
                            end.linkTo(parent.end,contentMargin)
                            bottom.linkTo(equal.top)
                        }
                        .aspectRatio(1f)
                    ,
                    color = MaterialTheme.colors.secondary
                ) {
                    calculatorViewModel?.onInfixChange("^")
                }

                CharacterItem(
                    char = "+",
                    modifier = Modifier
                        .width(50.dp)
                        .constrainAs(addition){
                            top.linkTo(minus.bottom,contentMargin)
                            start.linkTo(parent.start)
                            end.linkTo(multiplication.start)
                            bottom.linkTo(equal.top,contentMargin)
                        }
                        .aspectRatio(1f / 2f)
                    ,
                    color = MaterialTheme.colors.secondary
                ) {
                    calculatorViewModel?.onInfixChange("+")
                }
                CharacterItem(
                    char = "=",
                    modifier = Modifier
                        .height(50.dp)
                        .constrainAs(equal){
                            top.linkTo(power.bottom,contentMargin)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end,contentMargin)
                            bottom.linkTo(parent.bottom)
                        }
                        .aspectRatio(2f / 1f)
                    ,
                    color = MaterialTheme.colors.secondary
                ) {
                   calculatorViewModel?.evaluateExpression()
                }





















            }































            }


        }


    }


}








@Composable
fun CharacterItem(
    char: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.surface,
    onClick: () -> Unit,
) {
    Surface(
        shape = CircleShape,
        color = color,
        modifier = modifier
            .clip(CircleShape)
            .clickable {
                onClick.invoke()
            }
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = char,
                modifier = Modifier.padding(8.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.button
            )
        }

    }


}

@Preview(showSystemUi = true)
@Composable
fun PrevHomeCalculator() {
    CalculatorTheme {
        HomeCalculator(calculatorViewModel = null)
    }
}



























