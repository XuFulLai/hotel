<template>
    <div>
        <p>酒店id：{{ hotelDetails.hotleId }}</p>
    </div>
</template>

<script>
    export default {
        name: "HotelDetails",
        data(){
            return {
                hotelDetails: ''
            }
        },
        mounted(){
            this.getHotelDetails()
        },
        methods: {
            getHotelDetails(){
                this.hotelDetails = this.$route.params
            }
        }
    }
</script>

<style scoped>

</style>