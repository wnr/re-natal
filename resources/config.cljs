(ns env.config)

(def figwheel-urls {
                    {{#each platforms}}
                    :{{@key}} "ws://{{this.host}}:{{#if ../figwheelPort}}{{../figwheelPort}}{{else}}3449{{/if}}/figwheel-ws"
                    {{/each}}
                    })