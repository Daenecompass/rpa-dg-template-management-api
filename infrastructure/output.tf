
output "idam_api_url" {
  value = "${var.idam_api_url}"
}

output "s2s_url" {
  value = "http://${var.s2s_url}-${local.local_env}.service.core-compute-${local.local_env}.internal"
}

output "enable_idam_health_check" {
  value = "${var.enable_idam_healthcheck}"
}

output "enable_idam_healthcheck" {
  value = "${var.enable_idam_healthcheck}"
}
