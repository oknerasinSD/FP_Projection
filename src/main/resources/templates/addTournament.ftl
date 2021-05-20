<#import "parts/common.ftl" as c>

<@c.page>

    <form method="post" enctype="multipart/form-data">

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Название:</label>
            <div class="col-sm-6">
                <input type="text"
                       name="title"
                       class="form-control"
                       placeholder="Название турнира" />
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Лига:</label>
            <div class="col-sm-6">
                <select class="form-control" name="league">
                    <option value="" selected disabled>Выбор лиги</option>
                    <option>Англия: АПЛ</option>
                    <option>Испания: Ла Лига</option>
                    <option>Италия: Серия А</option>
                </select>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Дата старта:</label>
            <div class="col-sm-6">
                <input type="date" class="form-control" id="date" name="startDate" placeholder="Дата начала">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Время старта:</label>
            <div class="col-sm-6">
                <input type="time" class="form-control" id="time" name="startTime" placeholder="Время начала">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Дата окончания:</label>
            <div class="col-sm-6">
                <input type="date" class="form-control" id="date" name="endDate" placeholder="Дата окончания">
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Время окончания:</label>
            <div class="col-sm-6">
                <input type="time" class="form-control" id="time" name="endTime" placeholder="Время окончания">
            </div>
        </div>

        <div class="form-group row">
            <label for="exampleFormControlFile1" class="col-sm-2 col-form-label">Входные данные</label>
            <div class="col-sm-6">
                <input type="file" class="form-control-file" id="exampleFormControlFile1" name="file">
            </div>
        </div>



        <#--<div class="form-group row">
            <label for="exampleFormControlFile1" class="col-sm-2 col-form-label">Логотип</label>
            <div class="col-sm-6">
                <input type="file" class="form-control-file" id="exampleFormControlFile1" name="image">
            </div>
        </div>-->

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div>
            <button type="submit" class="btn btn-outline-dark btn-lg" style="margin-top: 2%">Добавить турнир</button>
        </div>

    </form>

</@c.page>