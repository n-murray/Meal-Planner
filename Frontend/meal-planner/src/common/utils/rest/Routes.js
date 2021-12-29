class Routes {

    static serverAddress = "";

    static resourceExt = "/res";
    static recipeExt = Routes.resourceExt + "/recipe";
    static takeawayExt = Routes.resourceExt + "/takeaway";
    static planExt = Routes.resourceExt + "/plan";

    static getAddress() {
        let href = window.location.origin;
        let port = process.env.REACT_APP_SERVER_PORT;
        if((href.match(/:/g) || []).length === 1) {
            Routes.serverAddress = href.substr(0, href.length).concat(":", port, "/api");
        } else {
            Routes.serverAddress = href.slice(0, href.lastIndexOf(":")).concat(":", port, "/api");
        }

        return Routes.serverAddress;
    }

    static getServerPort() {
        return window.location.hostname === "localhost" || window.location.hostname.substring(0,3) === "192" ?
            process.env.REACT_APP_DEVELOPMENT_SERVER_PORT
            :
            process.env.REACT_APP_SERVER_PORT;
    }
}

export default Routes;