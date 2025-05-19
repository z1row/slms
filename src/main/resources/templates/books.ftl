<!DOCTYPE html>
<html lang = "en">
<head>
    <meta charset="UTF-8">
    <title>Books List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f9f9f9;
            color: #333;
        }

        h1 {
            text-align: center;
            color: #2c3e50;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #fff;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #2980b9;
            color: white;
            text-transform: uppercase;
            letter-spacing: 0.1em;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        div {
            width: 80%;
            margin: 10px auto;
            text-align: center;
        }

        a {
            color: #2980b9;
            text-decoration: none;
            margin: 0 10px;
            font-weight: bold;
        }

        a:hover {
            text-decoration: underline;
        }

        a[disabled], a[disabled]:hover {
            color: #aaa;
            pointer-events: none;
            text-decoration: none;
        }
    </style>
</head>
<body>
    <h1>Books</h1>

    <table>
        <thead>
            <tr>
                <th style="width: 100px">Year</th>
                <th style="flex: 1">Title</th>
                <th>Author</th>
                <th>ISBN</th>
            </tr>
        </thead>
        <tbody>
        <#if response.content?size gt 0>
            <#list response.content as book>
                <tr>
                    <td>${book.publishedYear?c}</td>
                    <td>${book.title}</td>
                    <td>${book.author}</td>
                    <td>${book.isbn}</td>
                </tr>
            </#list>
        <#else>
            <tr><td colspan="3">No books found.</td></tr>
        </#if>
        </tbody>
    </table>

    <div>
        <#if response.totalPages gt 1>
            <div>
                <#if !response.first>
                    <a href="?page=${response.number -1}&size=${response.size}">Previous</a>
                <#else>
                    <a disabled>Previous</a>
                </#if>

                Page ${response.number + 1} of ${response.totalPages}

                <#if !response.last>
                    <a href="?page=${response.number + 1}&size=${response.size}">Next</a>
                <#else>
                    <a disabled>Next</a>
                </#if>
            </div>
        </#if>
    </div>
</body>
</html>
