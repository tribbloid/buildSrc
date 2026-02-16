_# Project Initialization

## Verify configuration

The following configuration files should be declared all the time:

- "./.mcp.json" (for Claude Code)
- "./opencode.jsonc" (for OpenCode), this file should minimally contain the following part:
  ```json
  {
    "$schema": "https://opencode.ai/config.json",
    "theme": "opencode",
    "autoupdate": true
  }
  ```

Verify them according to their respective format and schema. Create them if they don't exist.

For jsonc files, schema information is indexed by "$schema" key.

## Generate MCP configurations

All configuration files should contain MCP configurations, if they are missing, create them using the following
template:

```json
{
  "fetch": {
    "command": "uvx",
    "args": [
      "mcp-server-fetch"
    ]
  },
  "playwright": {
    "command": "npx",
    "args": [
      "@playwright/mcp@latest"
    ]
  },
  "memory": {
    "command": "npx",
    "args": [
      "-y",
      "@modelcontextprotocol/server-memory"
    ],
    "env": {
      "MEMORY_FILE_PATH": "/tmp/mcp-memory.json"
    },
    "disabled": false
  },
  "deepwiki": {
    "command": "npx",
    "args": [
      "-y",
      "mcp-remote",
      "https://mcp.deepwiki.com/mcp"
    ],
    "type": "stdio"
  },
  "jetbrains": {
    "type": "sse",
    "url": "http://localhost:64342/sse",
    "headers": {
      "IJ_MCP_SERVER_PROJECT_PATH": "./"
    }
  }
}
```

All relative paths should be converted to absolute paths.

## Verify configuration again

Make sure that your revision doesn't break existing format & schema._