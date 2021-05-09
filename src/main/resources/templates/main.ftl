<#import "parts/common.ftl" as c>

<@c.page>

    Список турниров

    <#--<div class="card-columns">
        <#list tournaments as tournament>
            <div class="card my-3">
                <#if tournament.filename??>
                    <img src="/img/${tournament.filename}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <b>${tournament.title}</b>
                    <i>${tournament.league}</i>
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>-->

</@c.page>