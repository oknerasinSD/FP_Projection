<#import "parts/common.ftl" as c>

<@c.page>

<table class="table table-bordered mt-3">
    <caption>Список игроков</caption>
    <thead class="thread-dark">
    <tr>
        <th scope="col">Игрок</th>
        <th scope="col">Ожидаемые очки</th>
    </tr>
    </thead>
    <tbody>
    <#list players as player>
    <tr>
        <td>${player.name}</td>
        <td>${player.expectedPoints}</td>
    </tr>
    </#list>
    </tbody>
</table>
</div>
</@c.page>