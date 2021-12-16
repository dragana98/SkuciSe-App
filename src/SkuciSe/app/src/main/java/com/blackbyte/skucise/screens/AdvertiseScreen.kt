package com.blackbyte.skucise.screens


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.data.amenityList
import com.blackbyte.skucise.data.listOfAmenities
import com.blackbyte.skucise.data.listOfObjects
import com.blackbyte.skucise.ui.theme.SkuciSeTheme

@Composable
fun AdvertiseScreen(
    returnToPreviousScreen: () -> Unit
) {
    Scaffold(
        topBar = { NavTopBar("Oglasi", returnToPreviousScreen = returnToPreviousScreen) },
        backgroundColor = MaterialTheme.colors.background
    ) {
        var shouldAddNewRealty by remember{mutableStateOf(false)}

        Column(modifier = Modifier
            .padding(20.dp)
            .fillMaxHeight() // verticalArrangement = Arrangement.Center
            .verticalScroll(rememberScrollState())
        ) {

            // Naslov oglasa
            Text(
                text = "Naslov oglasa ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(size = 10.dp))

            CustomTextField("Apartmani Petrovic...",modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.size(size = 29.dp))

            // Ponuda objekta
            Text(
                text = "Ovim oglasom se nudi ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(size = 5.dp))

            var clickedChipValue = remember { mutableStateOf("")}

            AdvertiseFilterChip(filters = listOfObjects, clickedValueB = clickedChipValue)

            Spacer(modifier = Modifier.size(size = 15.dp))

            Text(
                text = "Objekat je namenjen za ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(size = 15.dp))

            var selectedCategory = remember { mutableStateOf("") }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){

                RadioButton(
                    selected = selectedCategory.value == "iznajmljivanje",
                    onClick = {
                        selectedCategory.value = "iznajmljivanje"
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onBackground
                    )
                )
                Text("iznajmljivanje")

                Spacer(modifier = Modifier.size(size = 40.dp))

                RadioButton(
                    selected = selectedCategory.value == "kupovinu",
                    onClick = {
                        selectedCategory.value = "kupovinu"
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary,
                        unselectedColor = MaterialTheme.colors.onBackground
                    ))
                Text("kupovinu")

            }

            Spacer(modifier = Modifier.size(size = 30.dp))


            Text(
                text = "Lokacija objekta ",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(size = 10.dp))

            Column(
                modifier = Modifier
                    .padding(13.dp)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))

            ){
                Column(
                    modifier = Modifier
                        .padding(13.dp)
                ){
                    Text("Grad")

                    CustomTextField("Beograd",modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.size(size = 20.dp))

                    Text("Poštanski broj")

                    CustomTextField("11000",modifier = Modifier.width(117.dp))

                    Spacer(modifier = Modifier.size(size = 20.dp))

                    Text("Ulica")

                    CustomTextField("Kralja Milana 10",modifier = Modifier.fillMaxWidth())

                    Spacer(modifier = Modifier.size(size = 10.dp))
                }
            }


            // Detalji o objektu - Section

            Spacer(modifier = Modifier.size(size = 25.dp))

            Text(
                text = "Prostorni plan objekta",
                fontSize = 19.sp,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.size(size = 20.dp))

            Text("Ovde možete uneti sve podatke za stanove/apartmane u ponudi " +
                    "(kvadratura, raspored soba, i slično)."
            ,fontSize = 14.sp)

            Spacer(modifier = Modifier.size(size = 20.dp))

            Text("Ovde možete uneti sve podatke za svaki sprat Vašeg objekta.",
                fontSize = 14.sp)

            Spacer(modifier = Modifier.size(size = 25.dp))

            CallPopupDialog(unified = clickedChipValue.value, leasable = selectedCategory.value)

            Spacer(modifier = Modifier.size(size = 15.dp))

           Row(
               horizontalArrangement = Arrangement.Center,
               verticalAlignment = Alignment.CenterVertically
           ){

               Button(
                   onClick = {
                       // navigateToHomeScreen()
                   },
                   modifier = Modifier
                       .width(320.dp)
                       .height(40.dp)
                       .clip(shape = RoundedCornerShape(6.dp)),
                   colors = ButtonDefaults.buttonColors(
                       backgroundColor = Color.LightGray,
                       contentColor = Color.DarkGray
                   )
               ){
                   Text(text = "Dodat je novi objekat", color = Color.Gray, fontSize = 15.sp)
               }

               Spacer(modifier = Modifier.size(size = 10.dp))

               Button(
                   onClick = {
                       // navigateToHomeScreen()
                   }, modifier = Modifier
                       .width(45.dp)
                       .height(40.dp)
                       .clip(shape = RoundedCornerShape(6.dp)),
                   colors = ButtonDefaults.buttonColors(
                       backgroundColor = Color.LightGray,
                       contentColor = Color.DarkGray
                   )
               ){
                   Icon(
                       imageVector = Icons.Outlined.Delete,
                       contentDescription = "Bin",
                       modifier = Modifier.size(30.dp),
                       tint = Color.DarkGray

                   )
               }
           }

            // Amenities

            Column(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
            )
            {
                Spacer(modifier = Modifier.size(size = 45.dp))

                Text(
                    text = "Pogodnosti",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(size = 10.dp))

                Text(
                    text = "Izaberite sve pogodnosti koje su uključene u ponudu.",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                FilterChipAmenities(filters = amenityList)
            }

            // Opis oglasa

            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ){
                Spacer(modifier = Modifier.size(size = 15.dp))

                Text(
                    text = "Opis oglasa",
                    fontSize = 19.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(size = 10.dp))

                Text(
                    text = "    Stavite naglasak na prednosti objekta - " +
                            "da li su prodavnice, škole i autobuske stanice u blizini?",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(size = 20.dp))

                Text(
                    text = "  Možda je Vaš objekat udaljen od saobraćajnica i bez bučne atmosfere?" +
                            " Ovo je prilika da kupca ubedite da izabere Vašu ponudu!",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.size(size = 15.dp))

                var textDescription by remember { mutableStateOf("") }

                TextField(
                    value = textDescription,
                    onValueChange = {textDescription = it},
                    placeholder = { Text("Dodajte opis...")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

            }
            Spacer(modifier = Modifier.size(size = 20.dp))

            Button(
                onClick = {
                    // navigateToHomeScreen()
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Dodaj oglas", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdvertiseScreenPreview () {
    SkuciSeTheme {
        AdvertiseScreen({})
    }
}
