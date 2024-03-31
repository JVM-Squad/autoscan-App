package com.example.munchkin;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Carddeck extends AppCompatActivity {
    LinearLayout parentlayout;
    CardView selectedCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carddeck);
        parentlayout= findViewById(R.id.containerforcards);
        fillcards();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void fillcards(){
        for(int i=0; i<=20; i++){
            //Karte
            CardView cards = new CardView(this);
            float dpValue = 125f;
            float dpValue2 = 200f;// Change this value to your desired dp
            float density = getResources().getDisplayMetrics().density;
            int pixelValue1 = (int) (dpValue * density);
            int pixelValue2 = (int) (dpValue2 * density);


            LinearLayout.LayoutParams layoutParamscards = new LinearLayout.LayoutParams(pixelValue1,pixelValue2);

            layoutParamscards.setMargins(16,16,16,16);
            cards.setLayoutParams(layoutParamscards);
            cards.setForeground(getResources().getDrawable(R.drawable.bg_roundrect_ripple_light_border));
            float radius = 23f;
            int pixelValueradius = (int) (radius * density);
            cards.setRadius(pixelValueradius);
            //karteninhalt
            LinearLayout karteninhalt = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParamskarteninhalt = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.MATCH_PARENT);
            karteninhalt.setOrientation(LinearLayout.VERTICAL);
            karteninhalt.setLayoutParams(layoutParamskarteninhalt);


            //kartenname
            TextView kartenname = new TextView(this);
            LinearLayout.LayoutParams layoutParamskartenname = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            float margin = 8f;
            int pixelValuemargin = (int) (margin * density);
            layoutParamskartenname.setMargins(0,pixelValuemargin,pixelValuemargin,0);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.chewyregular);
            kartenname.setTypeface(typeface);
            kartenname.setText("Blauer Ritter");
            kartenname.setTextColor(getResources().getColor(R.color.black));
            kartenname.setTextSize(16);
            kartenname.setGravity(Gravity.CENTER);
            kartenname.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            kartenname.setLayoutParams(layoutParamskartenname);
            karteninhalt.addView(kartenname);

            ImageView kartenbild = new ImageView(this);
            float dpvalueimage = 100f;
            int pixelValueimage = (int) (dpvalueimage * density);
            LinearLayout.LayoutParams layoutParamskartenbild = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                   pixelValueimage);
            kartenbild.setImageResource(R.drawable.blue_knight);
            kartenbild.setLayoutParams(layoutParamskartenbild);
            karteninhalt.addView(kartenbild);

            TextView kartenbeschreibung = new TextView(this);
            LinearLayout.LayoutParams layoutParamskartenbeschreibung = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, // Breite
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            kartenbeschreibung.setText("Füge einem Monster im Rittering 2 Schadenspunkte zu");
            kartenbeschreibung.setTextColor(getResources().getColor(R.color.black));
            kartenbeschreibung.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            kartenbeschreibung.setTextSize(12);
            layoutParamskartenbeschreibung.setMargins(0,4,0,0);
            kartenbeschreibung.setTypeface(typeface);
            kartenbeschreibung.setLayoutParams(layoutParamskartenbeschreibung);
            karteninhalt.addView(kartenbeschreibung);

            cards.addView(karteninhalt);



            /*cards.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oncardclick(cards);
                    //buttonsvisible machen
                    //eventuell alte ausgewählte karte entleuchten
                }
            }));*/
            parentlayout.addView(cards);

        }
    }
    public void oncardclick(CardView card){
        Log.d("card", "Card was selected");
        if (card == selectedCard) {
            // Wenn ja, entferne die Markierung
            card.setBackgroundColor(Color.TRANSPARENT); // Setze die Hintergrundfarbe auf transparent
            selectedCard = null; // Setze die ausgewählte Karte zurück
        } else {
            // Wenn nein, markiere die Karte
            if (selectedCard != null) {
                // Entferne die Markierung von der vorher ausgewählten Karte, wenn eine vorhanden ist
                selectedCard.setBackgroundColor(Color.TRANSPARENT);

            }
            // Markiere die ausgewählte Karte
            card.setBackgroundColor(Color.RED); // Ändere die Hintergrundfarbe nach deinen Wünschen
            selectedCard = card; // Setze die ausgewählte Karte
        }
    }

    }

