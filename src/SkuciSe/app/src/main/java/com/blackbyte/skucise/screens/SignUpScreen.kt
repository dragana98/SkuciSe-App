package com.blackbyte.skucise.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.blackbyte.skucise.MainActivity.Companion.prefs
import com.blackbyte.skucise.components.*
import com.blackbyte.skucise.ui.theme.SkuciSeTheme
import java.time.LocalDate


@Composable
fun SignUpScreen(
    returnToPreviousScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit
) {
    var showCalendar: Boolean by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf(LocalDate.now()) }
    var countryCodeIdIndex by remember { mutableStateOf(196) } // Podrazumijevani indeks, Srbija
    var phoneNumber by remember { mutableStateOf("") }
    var documentTypeIndex by remember { mutableStateOf(0) }
    var documentNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val documentTypes = listOf("Lična karta", "Pasoš", "Vozačka dozvola")

    val countries = listOf(
        "🇦🇫 Avganistan (+93)",
        "🇦🇽 Alandska ostrva (+93)",
        "🇦🇱 Albanija (+355)",
        "🇩🇿 Alžir (+213)",
        "🇦🇸 Američka Samoa (+1-684)",
        "🇦🇩 Andora (+376)",
        "🇦🇴 Angola (+244)",
        "🇦🇮 Angvila (+1-264)",
        "🇦🇶 Antarktik (+672)",
        "🇦🇬 Antigva i Barbuda (+1-268)",
        "🇦🇷 Argentina (+54)",
        "🇦🇲 Jermenija (+374)",
        "🇦🇼 Aruba (+297)",
        "🇦🇺 Australija (+61)",
        "🇦🇹 Austrija (+43)",
        "🇦🇿 Azerbejdžan (+994)",
        "🇧🇸 Bahami (+1-242)",
        "🇧🇭 Bahrein (+973)",
        "🇧🇩 Bangladeš (+880)",
        "🇧🇧 Barbados (+1-246)",
        "🇧🇾 Belorusija (+375)",
        "🇧🇪 Belgija (+32)",
        "🇧🇿 Belize (+501)",
        "🇧🇯 Benin (+229)",
        "🇧🇲 Bermuda (+1-441)",
        "🇧🇹 Butan (+975)",
        "🇧🇴 Bolivija (+591)",
        "🇧🇦 Bosna i Hercegovina (+387)",
        "🇧🇼 Bocvana (+267)",
        "🇧🇻 Ostrvo Buve (+267)",
        "🇧🇷 Brazil (+55)",
        "🇮🇴 Britanska teritorija Indijskog okeana (+246)",
        "🇧🇳 Brunej (+673)",
        "🇧🇬 Bugarska (+359)",
        "🇧🇫 Burkina Faso (+226)",
        "🇧🇮 Burundi (+257)",
        "🇰🇭 Kambodža (+855)",
        "🇨🇲 Kamerun (+237)",
        "🇨🇦 Kanada (+1)",
        "🇨🇻 Zelenortska ostrva (+238)",
        "🇧🇶 Karipska Holandija (+238)",
        "🇰🇾 Kajmanska ostrva (+1-345)",
        "🇨🇫 Centralnoafrička Republika (+236)",
        "🇹🇩 Čad (+235)",
        "🇨🇱 Čile (+56)",
        "🇨🇳 Kina (+86)",
        "🇨🇽 Božićno ostrvo (+61)",
        "🇨🇨 Kokosova (Kiling) ostrva (+61)",
        "🇨🇴 Kolumbija (+57)",
        "🇰🇲 Komori (+269)",
        "🇨🇬 Republika Kongo (+242)",
        "🇨🇩 DR Kongo (+1-809, 1-829, 1-849)",
        "🇨🇰 Kukova ostrva (+682)",
        "🇨🇷 Kostarika (+506)",
        "🇨🇮 Obala Slonovače (Obala Slonovače) (+506)",
        "🇭🇷 Hrvatska (+385)",
        "🇨🇺 Kuba (+53)",
        "🇨🇼 Curacao (+599)",
        "🇨🇾 Kipar (+357)",
        "🇨🇿 Češka (+420)",
        "🇩🇰 Danska (+45)",
        "🇩🇯 Džibuti (+253)",
        "🇩🇲 Dominika (+1-767)",
        "🇩🇴 Dominikanska Republika (+1-809, 1-829, 1-849)",
        "🇪🇨 Ekvador (+593)",
        "🇪🇬 Egipat (+20)",
        "🇸🇻 Salvador (+503)",
        "🇬🇶 Ekvatorijalna Gvineja (+240)",
        "🇪🇷 Eritreja (+291)",
        "🇪🇪 Estonija (+372)",
        "🇸🇿 Esvatini (Svazilend) (+372)",
        "🇪🇹 Etiopija (+251)",
        "🇫🇰 Foklandska ostrva (+500)",
        "🇫🇴 Farska ostrva (+298)",
        "🇫🇯 Fidži (+679)",
        "🇫🇮 Finska (+358)",
        "🇫🇷 Francuska (+33)",
        "🇬🇫 Francuska Gvajana (+33)",
        "🇵🇫 Francuska Polinezija (+689)",
        "🇹🇫 Francuske južne i antarktičke zemlje (+689)",
        "🇬🇦 Gabon (+241)",
        "🇬🇲 Gambija (+220)",
        "🇬🇪 Gruzija (+995)",
        "🇩🇪 Nemačka (+49)",
        "🇬🇭 Gana (+233)",
        "🇬🇮 Gibraltar (+350)",
        "🇬🇷 Grčka (+30)",
        "🇬🇱 Grenland (+299)",
        "🇬🇩 Grenada (+1-473)",
        "🇬🇵 Gvadalupa (+1-473)",
        "🇬🇺 Guam (+1-671)",
        "🇬🇹 Gvatemala (+502)",
        "🇬🇬 Gernzi (+44-1481)",
        "🇬🇳 Gvineja (+224)",
        "🇬🇼 Gvineja Bisao (+245)",
        "🇬🇾 Gvajana (+592)",
        "🇭🇹 Haiti (+509)",
        "🇭🇲 Ostrvo Herd i Mekdonaldova ostrva (+509)",
        "🇭🇳 Honduras (+504)",
        "🇭🇰 Hong Kong (+852)",
        "🇭🇺 Mađarska (+36)",
        "🇮🇸 Island (+354)",
        "🇮🇳 Indija (+91)",
        "🇮🇩 Indonezija (+62)",
        "🇮🇷 Iran (+98)",
        "🇮🇶 Irak (+964)",
        "🇮🇪 Irska (+353)",
        "🇮🇲 Ostrvo Man (+44-1624)",
        "🇮🇱 Izrael (+972)",
        "🇮🇹 Italija (+39)",
        "🇯🇲 Jamajka (+1-876)",
        "🇯🇵 Japan (+81)",
        "🇯🇪 Džersi (+44-1534)",
        "🇯🇴 Jordan (+962)",
        "🇰🇿 Kazahstan (+7)",
        "🇰🇪 Kenija (+254)",
        "🇰🇮 Kiribati (+686)",
        "🇰🇵 Severna Koreja (+850)",
        "🇰🇷 Južna Koreja (+82)",
        "🇰🇼 Kuvajt (+965)",
        "🇰🇬 Kirgistan (+996)",
        "🇱🇦 Laos (+856)",
        "🇱🇻 Letonija (+371)",
        "🇱🇧 Liban (+961)",
        "🇱🇸 Lesoto (+266)",
        "🇱🇷 Liberija (+231)",
        "🇱🇾 Libija (+218)",
        "🇱🇮 Lihtenštajn (+423)",
        "🇱🇹 Litvanija (+370)",
        "🇱🇺 Luksemburg (+352)",
        "🇲🇴 Makao (+853)",
        "🇲🇬 Madagaskar (+261)",
        "🇲🇼 Malavi (+265)",
        "🇲🇾 Malezija (+60)",
        "🇲🇻 Maldivi (+960)",
        "🇲🇱 Mali (+223)",
        "🇲🇹 Malta (+356)",
        "🇲🇭 Maršalska ostrva (+692)",
        "🇲🇶 Martinik (+692)",
        "🇲🇷 Mauritanija (+222)",
        "🇲🇺 Mauricijus (+230)",
        "🇾🇹 Majot (+262)",
        "🇲🇽 Meksiko (+52)",
        "🇫🇲 Mikronezija (+691)",
        "🇲🇩 Moldavija (+373)",
        "🇲🇨 Monako (+377)",
        "🇲🇳 Mongolija (+976)",
        "🇲🇪 Crna Gora (+382)",
        "🇲🇸 Monserat (+1-664)",
        "🇲🇦 Maroko (+212)",
        "🇲🇿 Mozambik (+258)",
        "🇲🇲 Mjanmar (+95)",
        "🇳🇦 Namibija (+264)",
        "🇳🇷 Nauru (+674)",
        "🇳🇵 Nepal (+977)",
        "🇳🇱 Holandija (+31)",
        "🇳🇨 Nova Kaledonija (+687)",
        "🇳🇿 Novi Zeland (+64)",
        "🇳🇮 Nikaragva (+505)",
        "🇳🇪 Niger (+227)",
        "🇳🇬 Nigerija (+234)",
        "🇳🇺 Niue (+683)",
        "🇳🇫 Ostrvo Norfolk (+683)",
        "🇲🇰 Severna Makedonija (+850)",
        "🇲🇵 Severna Marijanska ostrva (+1-670)",
        "🇳🇴 Norveška (+47)",
        "🇴🇲 Oman (+968)",
        "🇵🇰 Pakistan (+92)",
        "🇵🇼 Palau (+680)",
        "🇵🇸 Palestina (+970)",
        "🇵🇦 Panama (+507)",
        "🇵🇬 Papua Nova Gvineja (+675)",
        "🇵🇾 Paragvaj (+595)",
        "🇵🇪 Peru (+51)",
        "🇵🇭 Filipini (+63)",
        "🇵🇳 Ostrva Pitkern (+64)",
        "🇵🇱 Poljska (+48)",
        "🇵🇹 Portugal (+351)",
        "🇵🇷 Portoriko (+1-787, 1-939)",
        "🇶🇦 Katar (+974)",
        "🇷🇪 Reunion (+262)",
        "🇷🇴 Rumunija (+40)",
        "🇷🇺 Rusija (+7)",
        "🇷🇼 Ruanda (+250)",
        "🇧🇱 Saint Barthelemi (+590)",
        "🇸🇭 Sveta Jelena, Vaznesenje i Tristan da Kunja (+290)",
        "🇰🇳 Sveti Kits i Nevis (+1-869)",
        "🇱🇨 Sveta Lucija (+1-758)",
        "🇲🇫 Sveti Martin (+590)",
        "🇵🇲 Sent Pjer i Mikelon (+508)",
        "🇻🇨 Sveti Vinsent i Grenadini (+1-784)",
        "🇼🇸 Samoa (+685)",
        "🇸🇲 San Marino (+378)",
        "🇸🇹 Sao Tome i Princip (+239)",
        "🇸🇦 Saudijska Arabija (+966)",
        "🇸🇳 Senegal (+221)",
        "🇷🇸 Srbija (+381)",
        "🇸🇨 Sejšeli (+248)",
        "🇸🇱 Sijera Leone (+232)",
        "🇸🇬 Singapur (+65)",
        "🇸🇽 Sint Marten (+1-721)",
        "🇸🇰 Slovačka (+421)",
        "🇸🇮 Slovenija (+386)",
        "🇸🇧 Solomonska ostrva (+677)",
        "🇸🇴 Somalija (+252)",
        "🇿🇦 Južna Afrika (+27)",
        "🇬🇸 Južna Džordžija (+27)",
        "🇸🇸 Južni Sudan (+211)",
        "🇪🇸 Španija (+34)",
        "🇱🇰 Šri Lanka (+94)",
        "🇸🇩 Sudan (+249)",
        "🇸🇷 Surinam (+597)",
        "🇸🇯 Svalbard i Jan Majen (+47)",
        "🇸🇪 Švedska (+46)",
        "🇨🇭 Švajcarska (+41)",
        "🇸🇾 Sirija (+963)",
        "🇹🇼 Tajvan (+886)",
        "🇹🇯 Tadžikistan (+992)",
        "🇹🇿 Tanzanija (+255)",
        "🇹🇭 Tajland (+66)",
        "🇹🇱 Istočni Timor (+66)",
        "🇹🇬 Togo (+228)",
        "🇹🇰 Tokelau (+690)",
        "🇹🇴 Tonga (+676)",
        "🇹🇹 Trinidad i Tobago (+1-868)",
        "🇹🇳 Tunis (+216)",
        "🇹🇷 Turska (+90)",
        "🇹🇲 Turkmenistan (+993)",
        "🇹🇨 Ostrva Turks i Kaikos (+1-649)",
        "🇹🇻 Tuvalu (+688)",
        "🇺🇬 Uganda (+256)",
        "🇺🇦 Ukrajina (+380)",
        "🇦🇪 Ujedinjeni Arapski Emirati (+971)",
        "🇬🇧 Ujedinjeno Kraljevstvo (+44)",
        "🇺🇸 Sjedinjene Američke Države (+1)",
        "🇺🇲 Mala spoljna ostrva Sjedinjenih Država (+1)",
        "🇺🇾 Urugvaj (+598)",
        "🇺🇿 Uzbekistan (+998)",
        "🇻🇺 Vanuatu (+678)",
        "🇻🇦 Vatikan (Sveta stolica) (+379)",
        "🇻🇪 Venecuela (+58)",
        "🇻🇳 Vijetnam (+84)",
        "🇻🇬 Britanska Devičanska Ostrva (+1-284)",
        "🇻🇮 Devičanska ostrva Sjedinjenih Država (+1)",
        "🇼🇫 Valis i Futuna (+681)",
        "🇪🇭 Zapadna Sahara (+212)",
        "🇾🇪 Jemen (+967)",
        "🇿🇲 Zambija (+260)",
        "🇿🇼 Zimbabve (+263)"
    )

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { NavTopBar("Registracija", returnToPreviousScreen = returnToPreviousScreen) },
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(all = 20.dp)
        ) {
            OutlinedInputField(
                "Ime",
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 18.dp))
            OutlinedInputField(
                "Prezime",
                onValueChange = { surname = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 20.dp))
            OutlinedInputField(
                "E-adresa",
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 20.dp))
            OutlinedButton(
                onClick = {
                    showCalendar = true
                },
                modifier = Modifier
                    .height(46.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Datum rođenja")
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "registration icon"
                    )
                }
            }
            Spacer(modifier = Modifier.size(size = 20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Spacer(modifier = Modifier.size(size = 6.dp))
                    DropdownButton(
                        hintText = countries[countryCodeIdIndex],
                        onSelectedIndex = { countryCodeIdIndex = it },
                        items = countries,
                        disabled = listOf(),
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
                Spacer(modifier = Modifier.size(size = 10.dp))
                OutlinedInputField(
                    "Broj telefona",
                    onValueChange = { phoneNumber = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.size(size = 20.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Spacer(modifier = Modifier.size(size = 6.dp))
                    DropdownButton(
                        hintText = documentTypes[documentTypeIndex],
                        items = documentTypes,
                        disabled = listOf(),
                        onSelectedIndex = { documentTypeIndex = it },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    )
                }
                Spacer(modifier = Modifier.size(size = 10.dp))
                OutlinedInputField(
                    "Broj isprave",
                    onValueChange = { documentNumber = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Više informacija", color = MaterialTheme.colors.primary)
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "registration icon",
                    modifier = Modifier.size(18.dp),
                    tint = MaterialTheme.colors.primary
                )
            }
            Spacer(modifier = Modifier.size(size = 20.dp))
            OutlinedPasswordField(
                "Lozinka",
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(size = 20.dp))
            if (showCalendar)
                Popup(onDismissRequest = { showCalendar = false }) {
                    Surface(color = MaterialTheme.colors.primaryVariant) {
                        DatePickerGrid(
                            date = dateOfBirth,
                            onDateSelected = { dateOfBirth = it }
                        )
                    }
                }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxHeight()
            ) {
                Button(
                    onClick = {
                        Log.d("DEBUG", "$name  $surname  $email  $dateOfBirth  $countryCodeIdIndex  $phoneNumber  $documentTypeIndex  $documentNumber  $password")
                        navigateToHomeScreen()
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Registrujte se")
                }
                Spacer(modifier = Modifier.size(size = 10.dp))
                Text(
                    text = "Imate nalog? Prijavite se.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScrenPreview() {
    SkuciSeTheme {
        SignUpScreen({}, {})
    }
}