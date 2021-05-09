<#import "parts/common.ftl" as c>

<@c.page>

    Список турниров

    <div class="card-columns">
        <#list tournaments as tournament>
            <div class="card my-3">
                <div class="m-2">
                    <div style="text-align: center">
                        <a href="/tournament?id=${tournament.id}" style="color: black">
                            <b>${tournament.title}</b>
                        </a>
                    </div>
                </div>
            </div>
        <#else>
            No tournaments
        </#list>
    </div>

</@c.page>