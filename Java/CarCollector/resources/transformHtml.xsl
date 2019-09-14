<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Collector</title>
            </head>
            <style>
                body{
                background-color: midnightblue;
                }

                table {
                font-family: 'Merriweather', serif;
                width: 100%;
                }

                td, th {
                border: 1px solid #dddddd;
                text-align: left;
                padding: 8px;
                color: cornsilk;
                }

                .header_font {
                font-family: 'Merriweather', serif;
                text-align: center;
                font-size: 36px;
                color: darkgoldenrod;
                }
            </style>
            <body>
                <h1 class="header_font">Collector</h1>
                <table>
                    <tr>
                        <td align="center"><strong>Brand</strong></td>
                        <td align="center"><strong>Country</strong></td>
                        <td align="center"><strong>Price</strong></td>
                    </tr>

                    <xsl:for-each select="collectors/collector">
                        <tr>
                            <td><xsl:value-of select="brand"/></td>
                            <td><xsl:value-of select="country"/></td>
                            <td><xsl:value-of select="price"/></td>
                        </tr>
                    </xsl:for-each>
                    <tr>
                        <td><strong>Average price: </strong></td>
                        <td><xsl:value-of select="sum(collectors/collector/price) div count (collectors/collector)"/></td>
                    </tr>

                    <tr>
                        <td>Summary price: </td>
                        <td><xsl:value-of select="sum(collectors/collector/price)"/></td>
                    </tr>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>