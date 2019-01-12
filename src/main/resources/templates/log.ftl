<html>
<head>
    <title>Log</title>
    <link rel="stylesheet"
          href="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.13.1/styles/default.min.css">
    <script src="//cdnjs.cloudflare.com/ajax/libs/highlight.js/9.13.1/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
</head>
<body>
<table width="100%">
    <tr>
        <td>Message Code</td>
        <td>Visited At</td>
        <td>Path</td>
        <td>Address</td>
        <td>Ip Info</td>
        <td>User Agent</td>
    </tr>
    <#list logEntries as i>
        <tr>
        <td>${i.messageCode!""}</td>
        <td>${i.createdAt}</td>
        <td>${i.requestPath}</td>
        <td>${i.ipAddress!""}</td>
        <td>
        <pre><code class="json">${i.ipInfo!""}</code></pre>
        </td>
        <td>${i.userAgent}</td>
        </tr>
    </#list>
</table>
</body>
</html>
