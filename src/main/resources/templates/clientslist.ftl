<#import "parts/common.ftl" as c>

<@c.page>
<#if true>
<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/clients" class="form-inline">
            <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Поиск">
            <button type="submit" class="btn btn-primary ml-2">Поиск</button>
        </form>
    </div>
</div>
</#if>
<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Добавить нового клиента
</a>
<div class="collapse" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control" name="name" placeholder="Компания" />
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="address" placeholder="Адрес">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="phoneNumber" placeholder="Номер телефона">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </form>
    </div>
</div>

<#if true>
<table class="table table-bordered mt-3">
    <caption>Список клиентов</caption>
    <thead class="thread-dark">
    <tr>
        <th scope="col">Клиент</th>
        <th scope="col">Адрес</th>
        <th scope="col">Телефон</th>
    </tr>
    </thead>
    <tbody>
    <#list customers as customer>
    <tr>
        <td>${customer.name}</td>
        <td>${customer.address}</td>
        <td>${customer.phoneNumber}</td>
    </tr>
    </#list>
    </tbody>
</table>
<#else>
    <div>
        У вас пока нет клиентов
    </div>
</#if>
</div>
</@c.page>