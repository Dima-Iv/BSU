<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>
    <xsl:template match="/">

        <xsl:text>
        </xsl:text>
        <xsl:text>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        </xsl:text>
        <xsl:text>Brand   </xsl:text><xsl:text> x </xsl:text>
        <xsl:text>   Country  </xsl:text><xsl:text> x </xsl:text>
        <xsl:text>    Price
        </xsl:text>
        <xsl:text>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        </xsl:text>

        <xsl:for-each select="collectors/collector">
        <xsl:value-of select="brand"/><xsl:text>        </xsl:text>
        <xsl:value-of select="country"/><xsl:text>        </xsl:text>
        <xsl:value-of select="price"/><xsl:text>
        </xsl:text>
        </xsl:for-each>

        <xsl:text>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        </xsl:text>

        <xsl:value-of select="concat('Average price: ', sum(collectors/collector/price) div count (collectors/collector))"/>
        <xsl:text>
        </xsl:text>
        <xsl:value-of select="concat('Summary price: ', sum(collectors/collector/price))"/>
        <xsl:text>
        </xsl:text>
        <xsl:text>xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
        </xsl:text>

    </xsl:template>
</xsl:stylesheet>