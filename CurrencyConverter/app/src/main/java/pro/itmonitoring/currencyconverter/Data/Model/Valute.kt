package pro.itmonitoring.currencyconverter.Data.Model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "Valute", strict = false)
class Valute {
    @field:Attribute(name = "ID")
    var ID: String? = null
    @field:Element(name = "CharCode")
    var CharCode: String? = null
    @field:Element(name = "Name")
    var Name: String? = null
    @field:Element(name = "Nominal")
    var Nominal: String? = null
    @field:Element(name = "NumCode")
    var NumCode: String? = null
    @field:Element(name = "Value")
    var Value: String? = null
}