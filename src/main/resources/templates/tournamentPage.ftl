<#import "parts/common.ftl" as c>

<@c.page>

    <form method="post">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 1:</label>
            <div class="col-sm-6">
                <select class="form-control" name="team1">
                    <option value="" selected disabled>Выбор команды</option>
                    <#list teams as team>
                        <option>${team}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 2:</label>
            <div class="col-sm-6">
                <select class="form-control" name="team2">
                    <option value="" selected disabled>Выбор команды</option>
                    <#list teams as team>
                        <option>${team}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 3:</label>
            <div class="col-sm-6">
                <select class="form-control" name="team3">
                    <option value="" selected disabled>Выбор команды</option>
                    <#list teams as team>
                        <option>${team}</option>
                    </#list>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Команда 4:</label>
            <div class="col-sm-6">
                <select class="form-control" name="team4">
                    <option value="" selected disabled>Выбор команды</option>
                    <#list teams as team>
                        <option>${team}</option>
                    </#list>
                </select>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div>
            <button type="submit" class="btn btn-primary">Подобрать команду</button>
        </div>

    </form>

</@c.page>