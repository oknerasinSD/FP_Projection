<#import "parts/common.ftl" as c>

<@c.page>

    <h1 class="display-3">Ваш фэнтези-состав</h1>

    <table class="table table-striped">

        <thead>
        <tr>
            <th scope="col">Игрок</th>
            <th scope="col">Команда</th>
            <th scope="col">Позиция</th>
            <th scope="col">Цена</th>
            <th scope="col">Ожидаемые очки</th>
        </tr>
        </thead>

        <tbody>
        <#list players as player>
            <tr>
                <td>${player.name}</td>
                <td>${player.team}</td>
                <td>${player.position}</td>
                <td>${player.price}</td>
                <td>${player.expectedPoints}</td>
            </tr>
        </#list>
        </tbody>

    </table>

</@c.page>