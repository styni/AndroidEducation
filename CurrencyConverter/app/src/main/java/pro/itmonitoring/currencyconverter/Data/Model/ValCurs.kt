package pro.itmonitoring.currencyconverter.Data.Model

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "Currency", strict = false)
class ValCurs (
    @field:Attribute(name = "Date")
    var date: String? = null,

    @field:Attribute(required = false)
    var name: String? = null,

    @field:ElementList(inline = true, required = false)
    var list: List<Valute>? = null
)