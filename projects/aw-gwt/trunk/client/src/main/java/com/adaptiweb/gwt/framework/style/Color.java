package com.adaptiweb.gwt.framework.style;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.Element;

/**
 * <b>CSS Color Names</b><br>
 * The table below provides a list of the color names 
 * that are supported by all major browsers.<br>
 * 
 * <br><b>Note:</b> If you want your pages to validate with an HTML or a CSS validator, 
 * W3C has listed 16 color names that you can use: {@link #Aqua}, {@link #Black}, {@link #Blue}, 
 * {@link #Fuchsia}, {@link #Gray}, {@link #Green}, {@link #Lime}, {@link #Maroon}, {@link #Navy}, 
 * {@link #Olive}, {@link #Purple}, {@link #Red}, {@link #Silver}, {@link #Teal}, {@link #White}, 
 * and {@link #Yellow}.<br>
 * If you want to use other colors, you must specify their RGB or HEX value.<br>
 * 
 * <br>Implemented by: <a href="http://www.w3schools.com/CSS/css_colornames.asp">http://www.w3schools.com/CSS/css_colornames.asp</a>
 * 
 * @author <a href="mailto:milan.skuhra@anasoft.sk">Milan Skuhra</a>
 */
public enum Color implements ColorType {
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>AliceBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F0F8FF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: AliceBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	AliceBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>AntiqueWhite</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FAEBD7</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: AntiqueWhite; border: 1px solid black"></td></tr>
	 * </table>
	 */
	AntiqueWhite,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Aqua</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00FFFF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Aqua; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Aqua,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Aquamarine</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#7FFFD4</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Aquamarine; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Aquamarine,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Azure</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F0FFFF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Azure; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Azure,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Beige</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F5F5DC</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Beige; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Beige,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Bisque</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFE4C4</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Bisque; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Bisque,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Black</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#000000</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Black; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Black,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>BlanchedAlmond</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFEBCD</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: BlanchedAlmond; border: 1px solid black"></td></tr>
	 * </table>
	 */
	BlanchedAlmond,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Blue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#0000FF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Blue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Blue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>BlueViolet</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#8A2BE2</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: BlueViolet; border: 1px solid black"></td></tr>
	 * </table>
	 */
	BlueViolet,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Brown</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#A52A2A</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Brown; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Brown,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>BurlyWood</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#DEB887</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: BurlyWood; border: 1px solid black"></td></tr>
	 * </table>
	 */
	BurlyWood,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>CadetBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#5F9EA0</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: CadetBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	CadetBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Chartreuse</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#7FFF00</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Chartreuse; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Chartreuse,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Chocolate</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#D2691E</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Chocolate; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Chocolate,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Coral</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF7F50</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Coral; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Coral,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>CornflowerBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#6495ED</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: CornflowerBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	CornflowerBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Cornsilk</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFF8DC</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Cornsilk; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Cornsilk,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Crimson</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#DC143C</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Crimson; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Crimson,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Cyan</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00FFFF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Cyan; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Cyan,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00008B</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkCyan</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#008B8B</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkCyan; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkCyan,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkGoldenRod</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#B8860B</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkGoldenRod; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkGoldenRod,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkGray</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#A9A9A9</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkGray; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkGray,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkGrey</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#A9A9A9</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkGrey; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkGrey,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#006400</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkKhaki</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#BDB76B</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkKhaki; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkKhaki,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkMagenta</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#8B008B</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkMagenta; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkMagenta,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkOliveGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#556B2F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkOliveGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkOliveGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Darkorange</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF8C00</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Darkorange; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Darkorange,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkOrchid</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#9932CC</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkOrchid; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkOrchid,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkRed</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#8B0000</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkRed; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkRed,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkSalmon</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#E9967A</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkSalmon; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkSalmon,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkSeaGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#8FBC8F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkSeaGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkSeaGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkSlateBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#483D8B</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkSlateBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkSlateBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkSlateGray</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#2F4F4F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkSlateGray; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkSlateGray,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkSlateGrey</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#2F4F4F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkSlateGrey; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkSlateGrey,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkTurquoise</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00CED1</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkTurquoise; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkTurquoise,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DarkViolet</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#9400D3</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DarkViolet; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DarkViolet,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DeepPink</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF1493</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DeepPink; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DeepPink,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DeepSkyBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00BFFF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DeepSkyBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DeepSkyBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DimGray</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#696969</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DimGray; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DimGray,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DimGrey</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#696969</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DimGrey; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DimGrey,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>DodgerBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#1E90FF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: DodgerBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	DodgerBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>FireBrick</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#B22222</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: FireBrick; border: 1px solid black"></td></tr>
	 * </table>
	 */
	FireBrick,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>FloralWhite</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFFAF0</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: FloralWhite; border: 1px solid black"></td></tr>
	 * </table>
	 */
	FloralWhite,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>ForestGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#228B22</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: ForestGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	ForestGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Fuchsia</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF00FF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Fuchsia; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Fuchsia,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Gainsboro</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#DCDCDC</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Gainsboro; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Gainsboro,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>GhostWhite</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F8F8FF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: GhostWhite; border: 1px solid black"></td></tr>
	 * </table>
	 */
	GhostWhite,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Gold</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFD700</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Gold; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Gold,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>GoldenRod</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#DAA520</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: GoldenRod; border: 1px solid black"></td></tr>
	 * </table>
	 */
	GoldenRod,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Gray</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#808080</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Gray; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Gray,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Grey</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#808080</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Grey; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Grey,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Green</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#008000</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Green; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Green,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>GreenYellow</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#ADFF2F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: GreenYellow; border: 1px solid black"></td></tr>
	 * </table>
	 */
	GreenYellow,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>HoneyDew</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F0FFF0</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: HoneyDew; border: 1px solid black"></td></tr>
	 * </table>
	 */
	HoneyDew,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>HotPink</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF69B4</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: HotPink; border: 1px solid black"></td></tr>
	 * </table>
	 */
	HotPink,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>IndianRed</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#CD5C5C</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: IndianRed; border: 1px solid black"></td></tr>
	 * </table>
	 */
	IndianRed,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Indigo</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#4B0082</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Indigo; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Indigo,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Ivory</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFFFF0</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Ivory; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Ivory,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Khaki</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F0E68C</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Khaki; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Khaki,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Lavender</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#E6E6FA</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Lavender; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Lavender,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LavenderBlush</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFF0F5</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LavenderBlush; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LavenderBlush,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LawnGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#7CFC00</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LawnGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LawnGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LemonChiffon</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFFACD</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LemonChiffon; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LemonChiffon,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#ADD8E6</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightCoral</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F08080</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightCoral; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightCoral,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightCyan</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#E0FFFF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightCyan; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightCyan,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightGoldenRodYellow</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FAFAD2</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightGoldenRodYellow; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightGoldenRodYellow,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightGray</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#D3D3D3</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightGray; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightGray,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightGrey</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#D3D3D3</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightGrey; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightGrey,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#90EE90</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightPink</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFB6C1</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightPink; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightPink,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightSalmon</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFA07A</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightSalmon; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightSalmon,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightSeaGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#20B2AA</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightSeaGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightSeaGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightSkyBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#87CEFA</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightSkyBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightSkyBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightSlateGray</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#778899</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightSlateGray; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightSlateGray,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightSlateGrey</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#778899</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightSlateGrey; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightSlateGrey,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightSteelBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#B0C4DE</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightSteelBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightSteelBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LightYellow</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFFFE0</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LightYellow; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LightYellow,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Lime</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00FF00</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Lime; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Lime,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>LimeGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#32CD32</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: LimeGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	LimeGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Linen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FAF0E6</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Linen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Linen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Magenta</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF00FF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Magenta; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Magenta,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Maroon</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#800000</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Maroon; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Maroon,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumAquaMarine</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#66CDAA</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumAquaMarine; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumAquaMarine,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#0000CD</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumOrchid</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#BA55D3</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumOrchid; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumOrchid,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumPurple</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#9370D8</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumPurple; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumPurple,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumSeaGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#3CB371</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumSeaGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumSeaGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumSlateBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#7B68EE</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumSlateBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumSlateBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumSpringGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00FA9A</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumSpringGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumSpringGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumTurquoise</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#48D1CC</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumTurquoise; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumTurquoise,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MediumVioletRed</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#C71585</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MediumVioletRed; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MediumVioletRed,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MidnightBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#191970</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MidnightBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MidnightBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MintCream</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F5FFFA</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MintCream; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MintCream,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>MistyRose</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFE4E1</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: MistyRose; border: 1px solid black"></td></tr>
	 * </table>
	 */
	MistyRose,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Moccasin</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFE4B5</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Moccasin; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Moccasin,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>NavajoWhite</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFDEAD</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: NavajoWhite; border: 1px solid black"></td></tr>
	 * </table>
	 */
	NavajoWhite,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Navy</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#000080</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Navy; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Navy,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>OldLace</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FDF5E6</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: OldLace; border: 1px solid black"></td></tr>
	 * </table>
	 */
	OldLace,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Olive</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#808000</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Olive; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Olive,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>OliveDrab</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#6B8E23</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: OliveDrab; border: 1px solid black"></td></tr>
	 * </table>
	 */
	OliveDrab,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Orange</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFA500</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Orange; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Orange,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>OrangeRed</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF4500</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: OrangeRed; border: 1px solid black"></td></tr>
	 * </table>
	 */
	OrangeRed,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Orchid</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#DA70D6</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Orchid; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Orchid,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>PaleGoldenRod</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#EEE8AA</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: PaleGoldenRod; border: 1px solid black"></td></tr>
	 * </table>
	 */
	PaleGoldenRod,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>PaleGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#98FB98</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: PaleGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	PaleGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>PaleTurquoise</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#AFEEEE</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: PaleTurquoise; border: 1px solid black"></td></tr>
	 * </table>
	 */
	PaleTurquoise,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>PaleVioletRed</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#D87093</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: PaleVioletRed; border: 1px solid black"></td></tr>
	 * </table>
	 */
	PaleVioletRed,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>PapayaWhip</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFEFD5</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: PapayaWhip; border: 1px solid black"></td></tr>
	 * </table>
	 */
	PapayaWhip,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>PeachPuff</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFDAB9</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: PeachPuff; border: 1px solid black"></td></tr>
	 * </table>
	 */
	PeachPuff,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Peru</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#CD853F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Peru; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Peru,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Pink</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFC0CB</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Pink; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Pink,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Plum</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#DDA0DD</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Plum; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Plum,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>PowderBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#B0E0E6</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: PowderBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	PowderBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Purple</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#800080</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Purple; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Purple,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Red</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF0000</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Red; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Red,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>RosyBrown</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#BC8F8F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: RosyBrown; border: 1px solid black"></td></tr>
	 * </table>
	 */
	RosyBrown,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>RoyalBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#4169E1</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: RoyalBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	RoyalBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SaddleBrown</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#8B4513</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SaddleBrown; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SaddleBrown,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Salmon</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FA8072</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Salmon; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Salmon,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SandyBrown</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F4A460</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SandyBrown; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SandyBrown,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SeaGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#2E8B57</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SeaGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SeaGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SeaShell</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFF5EE</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SeaShell; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SeaShell,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Sienna</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#A0522D</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Sienna; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Sienna,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Silver</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#C0C0C0</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Silver; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Silver,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SkyBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#87CEEB</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SkyBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SkyBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SlateBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#6A5ACD</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SlateBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SlateBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SlateGray</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#708090</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SlateGray; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SlateGray,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SlateGrey</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#708090</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SlateGrey; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SlateGrey,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Snow</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFFAFA</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Snow; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Snow,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SpringGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#00FF7F</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SpringGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SpringGreen,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>SteelBlue</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#4682B4</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: SteelBlue; border: 1px solid black"></td></tr>
	 * </table>
	 */
	SteelBlue,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Tan</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#D2B48C</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Tan; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Tan,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Teal</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#008080</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Teal; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Teal,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Thistle</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#D8BFD8</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Thistle; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Thistle,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Tomato</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FF6347</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Tomato; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Tomato,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Turquoise</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#40E0D0</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Turquoise; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Turquoise,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Violet</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#EE82EE</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Violet; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Violet,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Wheat</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F5DEB3</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Wheat; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Wheat,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>White</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFFFFF</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: White; border: 1px solid black"></td></tr>
	 * </table>
	 */
	White,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>WhiteSmoke</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#F5F5F5</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: WhiteSmoke; border: 1px solid black"></td></tr>
	 * </table>
	 */
	WhiteSmoke,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>Yellow</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#FFFF00</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: Yellow; border: 1px solid black"></td></tr>
	 * </table>
	 */
	Yellow,
	/**
	 * <table border="0">
	 * <tr><td class="label">Color Name</td><td>YellowGreen</td></tr>
	 * <tr><td class="label">Color HEX</td><td>#9ACD32</td></tr>
	 * <tr><td class="label">Color</td><td style="background-color: YellowGreen; border: 1px solid black"></td></tr>
	 * </table>
	 */
	YellowGreen,
	;

	private final Map<String, Style> styles = new HashMap<String, Style>();

	public Style colorStyle() {
		return customStyle("color");
	}

	public Style backgroundColorStyle() {
		return customStyle("background-color");
	}

	private Style customStyle(final String stylePropertyName) {
		if(!styles.containsKey(stylePropertyName)) {
			styles.put(stylePropertyName, new Style() {
				public void apply(Element element) {
					element.getStyle().setProperty(stylePropertyName, colorValue());
				}
			});
		}
		return styles.get(stylePropertyName);
	}
	
	private static ColorType custom(final String color) {
		return new ColorType() {
			public String colorValue() {
				return color;
			}
		};
	}

	public static ColorType custom(int r, int g, int b) {
		return custom("rgb(" + r + "," + g + "," + b + ")");
	}

	public static ColorType custom(int hexvalue) {
		String value = Integer.toHexString(hexvalue);

		if(value.length() > 6)
			throw new IllegalArgumentException("#" + value);

		return custom("#00000".substring(0, 7 - value.length()) + value);
	}

	public String colorValue() {
		return name().toLowerCase();
	}
}
