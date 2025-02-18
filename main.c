#include <stdio.h>
#include "freertos/FreeRTOS.h"
#include "freertos/task.h"
#include "freertos/queue.h"
#include "driver/gpio.h"
#define GPIO_OUTPUT_IO 4
#define GPIO_OUTPUT_PIN_SEL (1ULL<<GPIO_OUTPUT_IO)

#define GPIO_INPUT_IO 5
#define GPIO_INPUT_PIN_SEL (1ULL<<GPIO_INPUT_IO)
static QueueHandle_t gpio_evt_queue = NULL;

static void gpio_task_example(void* arg)
{
    uint32_t io_num;
    for (;;) {
        if (xQueueReceive(gpio_evt_queue, &io_num, portMAX_DELAY)) {
            printf("GPIO[%"PRIu32"] intr, val: %d\n", io_num, gpio_get_level(io_num));
        }
    }
}

void app_main() {
//zero-initialize the config structure.
gpio_config_t io_conf = {};
//disable interrupt
io_conf.intr_type = GPIO_INTR_DISABLE;
//set as output mode
io_conf.mode = GPIO_MODE_OUTPUT;
//bit mask of the pins that you want to set
io_conf.pin_bit_mask = GPIO_OUTPUT_PIN_SEL;
//disable pull-down mode
io_conf.pull_down_en = 0;
//disable pull-up mode
io_conf.pull_up_en = 0;
//configure GPIO with the given settings
gpio_config(&io_conf);

gpio_evt_queue = xQueueCreate(10, sizeof(uint32_t));
    //start gpio task
xTaskCreate(gpio_task_example, "gpio_task_example", 2048, NULL, 10, NULL);


int cnt = 0;
while(1) {
printf("cnt: %d\n", cnt++);
vTaskDelay(500);

vTaskDelay(750);
gpio_set_level(GPIO_OUTPUT_IO, cnt % 2);
}
}
