import Routes from "./Routes";

class RestClient {

    static NO_CONTACT="Could not contact the backend resource";

    async getRecipe(recipeName) {
        const url = Routes.getAddress() + Routes.recipeExt + '/' + recipeName;
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    Accept: 'application/json'
                }
            });
            return await response.json()
        }catch(error) {
            return RestClient.NO_CONTACT;
        }
    }

    async getRecipes() {
        const url = Routes.getAddress() + Routes.recipeExt;
        try {
            const response = fetch(url, {
                method: 'GET',
                headers: {
                    Accept: 'application/json'
                }
            });
            return (await response).json()
        } catch(error) {
            return RestClient.NO_CONTACT;
        }
    }

    async getWeekPlan(weekOf) {
        const url = Routes.getAddress() + Routes.planExt + '/' + weekOf;
        try {
            const response = fetch(url, {
                method: 'GET',
                headers: {
                    Accept: 'application/json'
                }
            });
            return (await response).json()
        } catch(error) {
            return RestClient.NO_CONTACT;
        }



    }
}

export default RestClient;