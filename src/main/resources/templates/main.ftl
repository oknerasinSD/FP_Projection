<#import "parts/common.ftl" as c>

<@c.page>

    <h1 class="display-4">Список турниров</h1>

    <div class="card-columns" style="margin-top: 2%">
        <#list tournaments as tournament>
            <div class="card my-3">
                <div class="m-2">
                    <div style="text-align: center">
                        <a href="/tournament?id=${tournament.convertId()}" style="color: black">
                            <b>${tournament.title}</b>
                        </a>
                    </div>
                    <div style="text-align: center">
                        <a>Старт: ${tournament.startDate} ${tournament.getStringStartTime()}</a>
                    </div>
                    <div style="text-align: center">
                        <a>Окончание: ${tournament.endDate} ${tournament.getStringEndTime()}</a>
                    </div>

                    <div style="margin-top: 2%">
                        <img src="/img/${tournament.league}.jpg" class="rounded mx-auto d-block">
                    </div>

                </div>
            </div>
        <#else>
            No tournaments
        </#list>
    </div>

</@c.page>