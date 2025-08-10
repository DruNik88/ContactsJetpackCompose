package com.example.contactsjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorProducer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactDetails(
                contact = Contact(
                    name = "Евгений",
                    surname = "Андреевич",
                    familyName = "Лукашин",
                    isFavorite = true,
                    phone = "+7 495 495 95 95",
                    address = "г. Москва, 3-я улица Строителей, д. 25, кв. 12",
                    email = "ELukashin@practicum.ru"
                )
            )
        }
    }
}

enum class Info {
    PHONE,
    ADDRESS,
    EMAIL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetails(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        RenderingIcon(contact)
        SetInitials(contact)
        SetFamilyNameAndFavouriteContact(contact)
        InfoRow(Info.PHONE, contact)
        InfoRow(Info.ADDRESS, contact)
        InfoRow(Info.EMAIL, contact)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenderingIcon(contact: Contact) {

    Box(
        modifier = Modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (contact.imageRes == null) {
            Icon(
                painter = painterResource(R.drawable.ic_circle),
                contentDescription = null,
                tint = ColorProducer { Color.Gray }
            )
            Row {
                Text(
                    text = "${contact.name.take(1)} ${contact.familyName.take(1)}"
                )
            }

        } else {

            Image(
                painter = painterResource(R.drawable.ic_panda_250),
                contentDescription = null,
            )
        }
    }
}

@Composable
fun SetInitials(contact: Contact) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        contact.surname?.let {
            Text(
                text = "${contact.name} $it",
                style = MaterialTheme.typography.headlineSmall
            )
        } ?: Text(
            text = contact.name,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
fun SetFamilyNameAndFavouriteContact(contact: Contact) {
    Row(
        modifier = Modifier.padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = contact.familyName,
            style = MaterialTheme.typography.headlineMedium
        )
        if (contact.isFavorite) {
            Image(
                modifier = Modifier.padding(horizontal = 16.dp),
                painter = painterResource(android.R.drawable.star_big_on),
                contentDescription = null
            )
        }
    }
}

@Composable
fun InfoRow(data: Info, contact: Contact) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .weight(0.5f)
        )
        Row(
            modifier = Modifier
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End

        ) {
            Text(
                text = when (data) {
                    Info.PHONE -> {
                        stringResource(R.string.phone) + ":"
                    }

                    Info.ADDRESS -> {
                        stringResource(R.string.address) + ":"
                    }

                    Info.EMAIL -> {
                        contact.email?.let { stringResource(R.string.email) + ":" } ?: ""

                    }
                }
            )
        }
        Row(
            modifier = Modifier
                .weight(1.5f)
                .padding(start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = when (data) {
                    Info.PHONE -> {
                        contact.phone
                    }

                    Info.ADDRESS -> {
                        contact.address
                    }

                    Info.EMAIL -> {
                        contact.email ?: ""

                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun ContactDetailsNoImagePreview() {
    ContactDetails(
        contact = Contact(
            name = "Евгений",
            surname = "Андреевич",
            familyName = "Лукашин",
            isFavorite = true,
            phone = "+7 495 495 95 95",
            address = "г. Москва, 3-я улица Строителей, д. 25, кв. 12",
            email = "ELukashin@practicum.ru"
        )
    )
}

@Preview
@Composable
fun ContactDetailsWithImagePreview() {
    ContactDetails(
        contact = Contact(
            name = "Василий",
            familyName = "Кузякин",
            phone = " --- ",
            imageRes = 1,
            address = "Ивановская область, дер. Крутово, д. 4",
            email = null
        )
    )
}
