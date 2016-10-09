<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
<xsl:output method="text" omit-xml-declaration="yes" indent="no"/>
<xsl:template match="/">
<xsl:for-each select="//report">
<xsl:value-of select="concat(client-address,',',client-guid,',',request-time,',',service-guid,',',retries-request,',',packets-requested,',',packets-serviced,',',max-hole-size,'&#xA;')"/>
</xsl:for-each>
</xsl:template>
</xsl:stylesheet>