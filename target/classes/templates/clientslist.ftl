<#import "parts/common.ftl" as c>

<@c.page>
<#if messages?hasContent>
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
                <input type="text" class="form-control" name="text" placeholder="Компания" />
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="tag" placeholder="Адрес">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="number" placeholder="Номер телефона">
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Добавить</button>
            </div>
        </form>
    </div>
</div>

<div class="card-columns mt-2">
    <#list messages as message>
    <div class="card my-3">
        <#if message.filename??>
            <img src="/img/${message.filename}" class="card-img-top">
        </#if>
    <div class="m-2">
        <span>${message.text}</span>
        <i>${message.tag}</i>
    </div>
    <div class="card-footer text-muted">
        ${message.authorName}
    </div>
    </div>
<#else>
    <div>
        У вас пока нет клиентов
    </div>
</#list>
</div>
</@c.page>