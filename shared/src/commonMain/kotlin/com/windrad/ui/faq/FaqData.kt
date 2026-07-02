package com.windrad.ui.faq

data class FaqQuestion(
    val id: String,
    val question: String,
    val answer: String,
    val urls: List<String> = emptyList(),
)

data class FaqCategory(
    val title: String,
    val questions: List<FaqQuestion>,
)

val FAQ_CONTENT: List<FaqCategory> = listOf(
    FaqCategory(
        title = "Energie und Wirtschaft",
        questions = listOf(
            FaqQuestion(
                id = "energie-1",
                question = "Warum stehen Windräder so oft still?",
                answer = "Sie stehen zeitweise still, etwa bei zu wenig oder zu viel Wind, bei Netzengpässen oder aus Artenschutzgründen, aber das ist kein Dauerzustand; der Kernpunkt ist eher die schwankende Einspeisung, die mit Netzausbau, Speichern und Flexibilität ausgeglichen werden muss.",
                urls = listOf(
                    "https://www.naturwind.de/windcheck/",
                    "https://www.bmwk.de/Redaktion/DE/FAQ/Windenergie/faq-windenergie.html",
                ),
            ),
            FaqQuestion(
                id = "energie-2",
                question = "Bringen Windräder überhaupt etwas, wenn der Wind nicht immer weht?",
                answer = "Ja, aber Windstrom muss in ein flexibles Stromsystem eingebettet werden. Versorgungssicherheit entsteht nicht durch eine einzelne Technologie, sondern durch den Mix aus Wind, Solar, Speichern, flexibler Nachfrage und steuerbaren Kapazitäten. Wetterabhängigkeit ist also ein reales Systemthema, aber kein Argument gegen Windenergie.",
                urls = listOf(
                    "https://www.bundesnetzagentur.de/DE/Fachthemen/ElektrizitaetundGas/Versorgungssicherheit/start.html",
                ),
            ),
            FaqQuestion(
                id = "energie-3",
                question = "Machen Windräder Strom teurer?",
                answer = "Nein, pauschal lässt sich das nicht so sagen. Die reinen Stromgestehungskosten von Onshore-Wind gehören in Deutschland zu den niedrigsten unter den Kraftwerkstechnologien; ob Endkundenpreise steigen oder fallen, hängt aber zusätzlich von Netzkosten, Abgaben, Steuern, Marktpreisen und Systemintegration ab.",
                urls = listOf(
                    "https://www.ise.fraunhofer.de/de/veroeffentlichungen/studien/studie-stromgestehungskosten-erneuerbare-energien.html",
                ),
            ),
            FaqQuestion(
                id = "energie-4",
                question = "Lohnt sich Windenergie überhaupt energetisch?",
                answer = "Ja. Moderne Windenergieanlagen erzeugen über ihre Lebensdauer deutlich mehr Energie, als für Herstellung, Betrieb und Rückbau nötig ist; der energetische „Rückzahlpunkt“ wird typischerweise nach kurzer Zeit erreicht.",
                urls = listOf(
                    "https://www.naturwind.de/windcheck/",
                    "https://www.ise.fraunhofer.de/de/veroeffentlichungen/studien/studie-stromgestehungskosten-erneuerbare-energien.html",
                ),
            ),
            FaqQuestion(
                id = "energie-5",
                question = "Ersetzen Windräder wirklich fossile Energien?",
                answer = "Windenergie ersetzt fossile Stromerzeugung nicht immer eins zu eins in jeder Stunde, senkt aber im Jahresverlauf den Bedarf an fossiler Erzeugung und vermeidet Treibhausgasemissionen. Das Umweltbundesamt weist darauf hin, dass erneuerbare Energien in der Stromerzeugung den wichtigsten Beitrag zur Vermeidung von Treibhausgasen leisten.",
                urls = listOf(
                    "https://www.umweltbundesamt.de/daten/umweltzustand-trends/energie/erneuerbare-energien-vermiedene-treibhausgase",
                    "https://www.umweltbundesamt.de/themen/klima-energie/erneuerbare-energien/erneuerbare-energien-in-zahlen",
                ),
            ),
            FaqQuestion(
                id = "energie-6",
                question = "Verbrauchen Windräder zu viele Rohstoffe im Bau?",
                answer = "Windräder brauchen natürlich große Mengen an Stahl, Beton und teils Verbundwerkstoffen. Das ist ein realer Materialbedarf, aber ein Großteil der Komponenten ist recycelbar; die schwierigste Stelle bleiben derzeit die Rotorblätter, für die die Verfahren noch verbessert werden.",
                urls = listOf(
                    "https://www.naturwind.de/windcheck/",
                    "https://www.basicthinking.de/blog/2025/10/20/windenergie-mythen/",
                ),
            ),
            FaqQuestion(
                id = "energie-7",
                question = "Profitieren Gemeinden und Anwohner finanziell von Windparks?",
                answer = "Gemeinden können finanziell beteiligt werden, etwa über Zahlungen nach § 6 EEG von bis zu 0,2 Cent pro eingespeister Kilowattstunde. Es sind noch mehr Transparenz und großzügigere Benefits im Gespräch.",
                urls = listOf(
                    "https://www.gesetze-im-internet.de/eeg_2014/__6.html",
                    "https://www.fachagentur-wind-solar.de/veroeffentlichungen/mustervertraege/finanzielle-teilhabe-6-eeg-2023",
                ),
            ),
        ),
    ),
    FaqCategory(
        title = "Anwohnerschutz und Gesundheit",
        questions = listOf(
            FaqQuestion(
                id = "anwohner-1",
                question = "Macht Infraschall von Windrädern krank?",
                answer = "Nach dem Stand der Forschung gibt es keinen belastbaren Nachweis, dass der von Windenergieanlagen ausgehende Infraschall bei Einhaltung der Regeln gesundheitsschädlich ist.",
                urls = listOf(
                    "https://www.umweltbundesamt.de/publikationen/infraschall-einfach-erklaert",
                ),
            ),
            FaqQuestion(
                id = "anwohner-2",
                question = "Belasten Windräder Anwohner durch Lärm?",
                answer = "Lärm ist ein reales Thema, aber er ist reguliert: Windenergieanlagen müssen die Immissionsrichtwerte der TA Lärm einhalten und brauchen eine immissionsschutzrechtliche Genehmigung. Das heißt, es gibt einen klaren rechtlichen Rahmen, aber an einzelnen Standorten kann es trotzdem Konflikte geben, die geprüft und gelöst werden müssen.",
                urls = listOf(
                    "https://www.umweltbundesamt.de/themen/laerm/nachbarschaftslaerm-laerm-von-anlagen/laerm-von-windenergieanlagen",
                    "https://www.bmwk.de/Redaktion/DE/FAQ/Windenergie/faq-windenergie.html",
                ),
            ),
            FaqQuestion(
                id = "anwohner-3",
                question = "Sinkt der Immobilienwert durch Windräder automatisch?",
                answer = "Ein automatischer, allgemeingültiger Wertverlust lässt sich nicht belegen. Immobilienpreise hängen von vielen Faktoren ab; Windenergie kann lokal als Einfluss wahrgenommen werden, aber der Effekt ist nicht überall gleich.",
                urls = listOf(
                    "https://www.naturwind.de/windcheck/",
                    "https://www.bmwk.de/Redaktion/DE/FAQ/Windenergie/faq-windenergie.html",
                ),
            ),
            FaqQuestion(
                id = "anwohner-4",
                question = "Ist Schattenwurf ein echtes Problem?",
                answer = "Ja, Schattenwurf ist ein echtes Anwohner-Thema, aber grundsätzlich ein regulierbares. Er wird in Genehmigungsverfahren berücksichtigt, und technische Abschaltungen können die Belastung begrenzen.",
                urls = listOf(
                    "https://www.bmwk.de/Redaktion/DE/FAQ/Windenergie/faq-windenergie.html",
                    "https://www.umweltbundesamt.de/themen/laerm/nachbarschaftslaerm-laerm-von-anlagen/laerm-von-windenergieanlagen",
                ),
            ),
        ),
    ),
    FaqCategory(
        title = "Klima, Natur und Artenschutz",
        questions = listOf(
            FaqQuestion(
                id = "klima-1",
                question = "Helfen Windräder wirklich beim Klimaschutz?",
                answer = "Ja. Windstrom hat im Betrieb sehr geringe Treibhausgasemissionen, und der Ausbau von Windenergie ersetzt fossile Stromerzeugung und senkt damit die Emissionen im Stromsystem.",
                urls = listOf(
                    "https://www.ise.fraunhofer.de/de/veroeffentlichungen/studien/studie-stromgestehungskosten-erneuerbare-energien.html",
                    "https://www.naturwind.de/windcheck/",
                ),
            ),
            FaqQuestion(
                id = "klima-2",
                question = "Töten Windräder massenhaft Vögel und Fledermäuse?",
                answer = "Windräder können Vögel und Fledermäuse gefährden, besonders an sensiblen Standorten und bei bestimmten Arten. Das ist kein Mythos, aber auch kein Freifahrtschein gegen Windenergie: Mit Standortwahl, Abschaltungen und Schutzkonzepten lassen sich Risiken deutlich senken.",
                urls = listOf(
                    "https://www.nabu.de/tiere-und-pflanzen/voegel/gefaehrdungen/windenergie/03410.html",
                    "https://www.nabu.de/tiere-und-pflanzen/saeugetiere/fledermaeuse/wissen/15018.html",
                ),
            ),
            FaqQuestion(
                id = "klima-3",
                question = "Zerstören Windräder unsere Wälder?",
                answer = "Windenergieanlagen zerstören nicht grundsätzlich Wälder. Neue Standorte entstehen genauso in nicht-bewaldeten Gebieten. Aber Windenergie an bewaldeten Orten ist ein echter Zielkonflikt. Unter bestimmten ökologischen Bedingungen kann sie trotzdem zur Energiewende beitragen, aber Bau und Betrieb bedeuten Rodung, Erschließung, Lebensraumveränderung und besondere Risiken für Waldarten. Das Problem ist nicht kurzfristig „wegreguliert“, sondern braucht langfristig gute Flächenplanung, Ausschluss sensibler Wälder und bessere ökologische Begleitforschung.",
                urls = listOf(
                    "https://www.naturschutz-energiewende.de/fragenundantworten/278-studien-naturvertraegliche-windenergie-wald/",
                    "https://www.bfn.de/windenergie-im-wald",
                ),
            ),
            FaqQuestion(
                id = "klima-4",
                question = "Schädigen Windräder das Klima indirekt durch Luftverwirbelung?",
                answer = "Es gibt lokale mikroklimatische Effekte im Nahbereich, aber daraus folgt kein weltweiter Klimaeffekt im Sinn einer zusätzlichen Erwärmung durch Windräder. Der zentrale Klimawirkungsweg bleibt die Verdrängung fossiler Stromerzeugung durch erneuerbaren Strom.",
                urls = listOf(
                    "https://www.naturwind.de/windcheck/",
                    "https://www.basicthinking.de/blog/2025/10/20/windenergie-mythen/",
                ),
            ),
        ),
    ),
    FaqCategory(
        title = "Über die App",
        questions = listOf(
            FaqQuestion(
                id = "app-1",
                question = "Woher kommen die Daten zu den Windrädern?",
                answer = "Unsere Hauptquelle ist das offizielle Marktstammdatenregister (MaStR) der Bundesnetzagentur. Dort werden alle Stromerzeugungsanlagen in Deutschland gesetzlich erfasst.",
                urls = listOf(
                    "https://www.marktstammdatenregister.de/MaStR",
                ),
            ),
            FaqQuestion(
                id = "app-2",
                question = "Wie aktuell sind die Daten in der App?",
                answer = "Wir haben unsere Daten zuletzt am 30. Juni 2026 aktualisiert.",
            ),
            FaqQuestion(
                id = "app-3",
                question = "Ein Windrad in meiner Nähe fehlt oder die Daten stimmen nicht. Warum?",
                answer = "Da wir die Daten direkt aus dem offiziellen Register beziehen, kann es sein, dass der Betreiber die Anlage dort noch nicht oder fehlerhaft eingetragen hat.",
            ),
            FaqQuestion(
                id = "app-4",
                question = "Wie berechnen wir die erzeugte Energie in der Sammlung?",
                answer = "Wir multiplizieren die Nennleistung jeder gesammelten Anlage (in kW) mit typischen Volllaststunden und summieren das Ergebnis auf. Als Volllaststunden verwenden wir den Durchschnitt 2015–2025 laut Fraunhofer ISE.\n\nDie Formel:\nEnergie (MWh/Jahr) = Σ (Nennleistung in kW ÷ 1.000) × Volllaststunden\n→ Onshore: 1.756 h/Jahr\n→ Offshore: 3.193 h/Jahr\n\nAlle Nennleistungen stammen aus dem Marktstammdatenregister.",
                urls = listOf(
                    "https://www.energy-charts.info/downloads.html?l=de&c=DE",
                ),
            ),
            FaqQuestion(
                id = "app-5",
                question = "Wie berechnen wir die versorgten Haushalte in der Sammlung?",
                answer = "Wir teilen die erzeugte Jahresenergie durch den durchschnittlichen Haushaltsstromverbrauch in Deutschland (2.277 kWh/Jahr).\n\nDie Formel:\nHaushalte = Erzeugte Energie (kWh/Jahr) ÷ 2.277 kWh",
            ),
            FaqQuestion(
                id = "app-6",
                question = "Wie berechnen wir den vermiedenen CO₂-Ausstoß in der Sammlung?",
                answer = "Wir verwenden den vom Umweltbundesamt ermittelten Faktor für durch Windenergie vermiedene Treibhausgasemissionen. Der Wert basiert auf den Daten für Deutschland 2025: 101,3 Mio. t CO₂ wurden durch ca. 133,7 Mio. MWh Windstrom vermieden.\n\nDie Formel:\nVermiedenes CO₂ (t) = Erzeugte Energie (MWh/Jahr) × 0,75767 t/MWh",
                urls = listOf(
                    "https://www.umweltbundesamt.de/daten/umweltzustand-trends/energie/erneuerbare-energien-vermiedene-treibhausgase#methodische-hinweise",
                    "https://www.umweltbundesamt.de/system/files/medien/479/publikationen/2026-03/UBA_Erneuerbare%20Energien%20in%20Deutschland%202025.pdf",
                ),
            ),
            FaqQuestion(
                id = "app-7",
                question = "Wie berechnen wir das eingesparte Kühlwasser in der Sammlung?",
                answer = "Windenergie benötigt kein Kühlwasser – anders als Kohle-, Gas- oder Kernkraftwerke. Als Vergleichsmaßstab verwenden wir den UBA-Durchschnittswert für den Kühlwasserverbrauch konventioneller Kraftwerke: 6,9 Mrd. m³ für 273,5 Mrd. kWh Einspeisung (Daten 2022).\n\nDie Formel:\nEingespartes Kühlwasser (t) = Erzeugte Energie (MWh/Jahr) × 25,22852 t/MWh",
                urls = listOf(
                    "https://www.umweltbundesamt.de/daten/wasser/wasserwirtschaft/nichtoeffentliche-wasserversorgung#hoher-khlwasserbedarf-bei-der-energieversorgung",
                    "https://www.destatis.de/DE/Presse/Pressemitteilungen/2023/03/PD23_090_43312.html",
                ),
            ),
            FaqQuestion(
                id = "app-8",
                question = "Wie berechnen wir die ökologische Ausgleichsfläche in der Sammlung?",
                answer = "Nach der Bundeskompensationsverordnung müssen Eingriffe in Natur und Landschaft kompensiert werden. Wir rechnen pauschal mit 0,53 ha dauerhaft beanspruchter Fläche pro Anlage (Fundament + Zuwegung) und einem Ausgleichsverhältnis 1:1.\n\nDie Formel:\nAusgleichsfläche (ha) = Anzahl gesammelter Anlagen × 0,53 ha",
                urls = listOf(
                    "https://windradfrei-soe.de/flaechenverbrauch-durch-wka-am-cottaer-spitzberg/",
                ),
            ),
            FaqQuestion(
                id = "app-9",
                question = "Wie berechnen wir die E-Auto-Vollladungen in der Sammlung?",
                answer = "Wir teilen die erzeugte Jahresenergie durch die durchschnittliche nutzbare Batteriekapazität eines modernen Elektroautos. Laut EV Database liegt dieser Wert bei 73,6 kWh – das entspricht 13,587 Vollladungen pro MWh.\n\nDie Formel:\nVollladungen = Erzeugte Energie (MWh/Jahr) × 13,58696",
                urls = listOf(
                    "https://ev-database.org/de/cheatsheet/useable-battery-capacity-electric-car",
                ),
            ),
            FaqQuestion(
                id = "app-10",
                question = "Wie berechnen wir die Windpark-Auslastung in der Sammlung?",
                answer = "Wir summieren die typischen Volllaststunden aller gesammelten Anlagen – getrennt nach Onshore und Offshore. Die Werte basieren auf dem Durchschnitt 2015–2025 laut Fraunhofer ISE (Energy-Charts).\n\nDie Formel:\nVolllaststunden = Onshore-Anlagen × 1.756 h + Offshore-Anlagen × 3.193 h",
                urls = listOf(
                    "https://www.energy-charts.info/downloads.html?l=de&c=DE",
                ),
            ),
            FaqQuestion(
                id = "app-11",
                question = "Wie hängt die Windkraftleistung vom Wind ab?",
                answer = "Die erzeugte Leistung wächst mit der dritten Potenz der Windgeschwindigkeit: Verdoppelt sich der Wind, verachtfacht sich die Leistung. Deshalb sind windstarke Standorte so entscheidend.\n\nDie Formel:\nP = ρ/2 × A × v³\n→ ρ ≈ 1,22 kg/m³ (Luftdichte)\n→ A = π × r² (überstrichene Rotorfläche)\n→ v = Windgeschwindigkeit",
                urls = listOf(
                    "https://www.wind-energie.de/fileadmin/redaktion/dokumente/publikationen-oeffentlich/Service/Kita_und_Schulmaterial/Physikbroschuere-UTF-8_FINAL-2013-11-08.pdf",
                ),
            ),
        ),
    ),
)
