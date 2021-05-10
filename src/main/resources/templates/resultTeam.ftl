<#import "parts/common.ftl" as c>

<@c.page>

    <div style="align-content: center">

        <div style="align-content: center">
            <h1 class="display-3">
                <a type="text pull-left">Ваш фэнтези-состав</a>
                <a href="/download_csv_file?filename=${filename}"
                   class="btn btn-outline-dark btn-lg float-right"
                   style="margin-top: 2.5%">
                    Загрузить
                </a>
            </h1>
        </div>

        <div style="margin-top: 2%">
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
        </div>
    </div>

</@c.page>