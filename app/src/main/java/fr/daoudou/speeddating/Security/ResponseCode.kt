package fr.daoudou.speeddating.Security

class ResponseCode {

    enum class StatusCode(val code : Int){
        // Information
        Continue(100),
        SwitchingProtocols(101),
        Processing(102),

        // Success
        OK(200),
        Created(201),
        Accepted(202),
        NoContent(204),
        ResetContent(205),
        MultiStatus(207),

        //Redirection
        Found(302),
        NotModified(304),

        //Error client side
        BadRequest(400),
        Unauthorized(401),
        Forbidden(403),
        NotFound(404),
        NotAcceptable(406),
        RequestTimeOut(408),
        UpgradeRequired(428),
        Conflict(409)
    }





}